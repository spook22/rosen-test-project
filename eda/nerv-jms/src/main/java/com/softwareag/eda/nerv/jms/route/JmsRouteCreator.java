package com.softwareag.eda.nerv.jms.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.channel.JmsChannelProvider;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.jmx.JmxHelper;
import com.softwareag.eda.nerv.publish.EventPublishListener;

public class JmsRouteCreator implements EventPublishListener {
	
	private static final Logger logger = LoggerFactory.getLogger(JmsRouteCreator.class);

	private final Map<String, Route> routesCache = new HashMap<String, Route>();

	private final JmxHelper jmxHelper = new JmxHelper();
	
	private final JmsChannelProvider jmsChannelProvider;
	
	private final ContextProvider contextProvider;

	public JmsRouteCreator(ContextProvider contextProvider, ComponentNameProvider componentNameProvider) {
		this.contextProvider = contextProvider;
		jmsChannelProvider = new JmsChannelProvider(componentNameProvider);
	}

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
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Processing pre-publish notification for event %s on channel %s.", event, channel));
			}
			Route route = routesCache.get(channel);
			if (route == null) {
				if (!jmxHelper.routeExists("jmsRouteFor:" + channel)) {
					createRoute(channel, event.getType());
				}
			}
			break;
		default:
			break;
		}
	}

	private void createRoute(String channel, String eventType) throws NERVException {
		String jmsChannel = jmsChannelProvider.channel(eventType);
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Creating JMS route for event type %s from channel %s to JMS channel %s.", eventType, channel, jmsChannel));
		}
		JmsRouteBuilder builder = new JmsRouteBuilder(channel, jmsChannel);
		try {
			contextProvider.context().addRoutes(builder);
		} catch (Exception e) {
			throw new NERVException("Cannot add routes to context.", e);
		}
	}

}