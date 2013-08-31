package com.softwareag.eda.nerv.jmx;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerNotification;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import org.apache.camel.Route;

import com.softwareag.eda.nerv.NERVException;

public class JMXHelper implements NotificationListener {

	private static final String CAMEL_DOMAIN = "org.apache.camel";

	private MBeanServer mbServer;

	private ObjectName mserverName;

	private final Map<String, String> channelsCache = new HashMap<String, String>();

	public JMXHelper() throws NERVException {
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
			String name = routeName.getKeyProperty("name");
			String endpointUri = (String) mbServer.getAttribute(routeName, "EndpointUri");
			channelsCache.put(name, stripOffParameters(endpointUri));
		} catch (Exception e) {
			throw new NERVException("Cannot add route to cache.", e);
		}
	}

	public Route routeExists(String routeId) {
		// return channelsCache.containsKey(routeId);
		return null;
	}

	private Set<ObjectName> lookupMBeans(String type) throws MalformedObjectNameException {
		ObjectName mbeansName = new ObjectName(CAMEL_DOMAIN + "type=" + type + ",*");
		return mbServer.queryNames(mbeansName, null);
	}

	private static String stripOffParameters(String endpoint) {
		int delimPos = endpoint.indexOf('?');
		if (delimPos != -1) {
			endpoint = endpoint.substring(0, delimPos);
		}
		return endpoint;
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
						channelsCache.remove(mBeanName.getKeyProperty("name"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // TODO
		}
	}

}