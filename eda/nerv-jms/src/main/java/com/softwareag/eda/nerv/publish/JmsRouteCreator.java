package com.softwareag.eda.nerv.publish;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Route;

import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.jmx.JMXHelper;

public class JmsRouteCreator implements EventPublishListener {

	private final Map<String, Route> routesCache = new HashMap<String, Route>();

	private final JMXHelper jmxHelper = new JMXHelper();

	@Override
	public void onPublish(PublishOperation operation, String channel, Event event) {
		/*
		 * 1. Using JMX check if route exists for the given ID deducted from the
		 * event type. 2. Use cache to optimize performance and not go to JMX
		 * every time. 3. If the route does not exist - create it. 4. Decide
		 * when/where to register the default JMS component.
		 */
		switch (operation) {
		case PRE_PUBLISH:
			Route route = routesCache.get(channel);
			if (route == null) {
				route = routeExistsInJMX(channel);
				if (route == null) {
					createRoute(channel);
				}
			}
			break;
		default:
			break;
		}
	}

	private void createRoute(String channel) {
		// TODO Auto-generated method stub

	}

	private Route routeExistsInJMX(String routeId) {
		return jmxHelper.routeExists(routeId);
	}

}
