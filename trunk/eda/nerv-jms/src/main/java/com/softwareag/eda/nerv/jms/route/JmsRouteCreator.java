package com.softwareag.eda.nerv.jms.route;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.context.ContextProvider;
import com.softwareag.eda.nerv.jms.channel.JmsChannelProvider;
import com.softwareag.eda.nerv.jmx.JmxHelper;
import com.softwareag.eda.nerv.route.RouteCreator;

public class JmsRouteCreator implements RouteCreator {

	private static final Logger logger = LoggerFactory.getLogger(JmsRouteCreator.class);

	private final Set<String> routesCache = new HashSet<String>();

	private final JmxHelper jmxHelper = new JmxHelper();

	private final JmsChannelProvider jmsChannelProvider;

	private final ContextProvider contextProvider;

	public JmsRouteCreator(ContextProvider contextProvider, ComponentNameProvider componentNameProvider) {
		this.contextProvider = contextProvider;
		jmsChannelProvider = new JmsChannelProvider(componentNameProvider);
	}

	/*
	 * 1. Using JMX check if route exists for the given ID deducted from the event type.
	 * 
	 * 2. Use cache to optimize performance and not go to JMX every time.
	 * 
	 * 3. If the route does not exist - create it. 4. Decide when/where to register the default JMS component.
	 */
	@Override
	public void createRoute(String from, String eventType) throws NERVException {
		if (!routesCache.contains(from) && !jmxHelper.routeExists("jmsRouteFor:" + from)) {
			String jmsChannel = jmsChannelProvider.channel(eventType);
			if (logger.isInfoEnabled()) {
				logger.info(String.format("Creating JMS route for event type %s from channel %s to JMS channel %s.", eventType, from, jmsChannel));
			}
			JmsRouteBuilder builder = new JmsRouteBuilder(from, jmsChannel);
			try {
				contextProvider.context().addRoutes(builder);
			} catch (Exception e) {
				throw new NERVException("Cannot add routes to context.", e);
			}
		}
		routesCache.add(from);
	}

}
