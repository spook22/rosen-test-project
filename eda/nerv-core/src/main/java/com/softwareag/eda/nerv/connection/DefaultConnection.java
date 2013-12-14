package com.softwareag.eda.nerv.connection;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;

public class DefaultConnection extends AbstractConnection {

	public DefaultConnection(ContextProvider contextProvider, Publisher publisher, SubscriptionHandler subscriptionHandler) {
		super(contextProvider, publisher, subscriptionHandler);
	}

}
