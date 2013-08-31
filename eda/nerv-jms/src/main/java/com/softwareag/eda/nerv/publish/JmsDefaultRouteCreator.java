package com.softwareag.eda.nerv.publish;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Route;

import com.softwareag.eda.nerv.event.Event;

public class JmsDefaultRouteCreator implements EventPublishListener {

	private final Map<String, Route> routesCache = new HashMap<String, Route>();

	@Override
	public void onPublish(PublishOperation operation, Event event) {
		/*
		 * 1. Using JMX check if route exists for the given ID deducted from the
		 * event type. 2. Use cache to optimize performance and not go to JMX
		 * every time. 3. If the route does not exist - create it. 4. Decide
		 * when/where to register the default JMS component.
		 */
		switch (operation) {
		case PRE_PUBLISH:
			Route route = routesCache.get(event.getType());
			if (route == null) {
				route = checkUsingJmx(event.getType());
				if (route == null) {
					createRoute(event.getType());
				}
			}
			break;
		default:
			break;
		}
	}

	private void createRoute(String type) {
		// TODO Auto-generated method stub

	}

	private Route checkUsingJmx(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
