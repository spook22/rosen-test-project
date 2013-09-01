package com.softwareag.eda.nerv.jmx;

import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerNotification;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.NERVException;

public class JmxHelper implements NotificationListener {

	private static final Logger logger = LoggerFactory.getLogger(JmxHelper.class);

	private static final String CAMEL_DOMAIN = "org.apache.camel";

	private MBeanServer mbServer;

	private ObjectName mserverName;

	private final Set<String> routesCache = Collections.synchronizedSet(new HashSet<String>());

	public JmxHelper() throws NERVException {
		try {
			mbServer = ManagementFactory.getPlatformMBeanServer();
			mserverName = new ObjectName("JMImplementation:type=MBeanServerDelegate");
			mbServer.addNotificationListener(mserverName, this, null, null);
			populateCacheWithExistingRoutes();
		} catch (Exception e) {
			throw new NERVException("Cannot create JMX helper.", e);
		}
	}

	public void clean() throws JMException {
		mbServer.removeNotificationListener(mserverName, this);
	}

	private void addToCache(ObjectName routeName) throws NERVException {
		try {
			String uri = (String) mbServer.getAttribute(routeName, "EndpointUri");
			routesCache.add(uri);
		} catch (Exception e) {
			throw new NERVException("Cannot add route to cache.", e);
		}
	}

	public boolean routeExists(String routeId) {
		return routesCache.contains(routeId);
	}

	private Set<ObjectName> lookupMBeans(String type) throws MalformedObjectNameException {
		ObjectName mbeansName = new ObjectName(CAMEL_DOMAIN + ":" + "type=" + type + ",*");
		return mbServer.queryNames(mbeansName, null);
	}

	private void populateCacheWithExistingRoutes() throws JMException {
		Set<ObjectName> routes = lookupMBeans("routes");
		for (ObjectName routeName : routes) {
			addToCache(routeName);
		}
	}

	@Override
	public void handleNotification(Notification notification, Object handback) {
		try {
			if (notification instanceof MBeanServerNotification) {
				ObjectName mBeanName = ((MBeanServerNotification) notification).getMBeanName();
				if (mBeanName.getDomain().equals(CAMEL_DOMAIN) && mBeanName.getKeyProperty("type").equals("routes")) {
					if (notification.getType().equals(MBeanServerNotification.REGISTRATION_NOTIFICATION)) {
						addToCache(mBeanName);
					} else if (notification.getType().equals(MBeanServerNotification.UNREGISTRATION_NOTIFICATION)) {
						routesCache.remove(mBeanName.getKeyProperty("name"));
					}
				}
			}
		} catch (Exception e) {
			logger.error("Cannot handle JMX notification: " + notification, e);
		}
	}

}