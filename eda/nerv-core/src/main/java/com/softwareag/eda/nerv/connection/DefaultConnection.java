package com.softwareag.eda.nerv.connection;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.context.ContextProvider;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.route.NERVDefaultRouteBuilder;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;

public class DefaultConnection extends AbstractConnection {

	public DefaultConnection(ContextProvider contextProvider, Publisher publisher, SubscriptionHandler subscriptionHandler) {
		super(contextProvider, publisher, subscriptionHandler);
		startRoutes();
	}

	private void startRoutes() throws NERVException {
		try {
			NERVDefaultRouteBuilder routeBuilder = new NERVDefaultRouteBuilder("direct-vm:nerv");
			context().addRoutes(routeBuilder);
		} catch (Exception e) {
			throw new NERVException("Cannot start NERV routes.", e);
		}
	}

}
