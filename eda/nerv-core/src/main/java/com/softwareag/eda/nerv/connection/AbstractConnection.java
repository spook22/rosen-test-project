package com.softwareag.eda.nerv.connection;

import java.util.Map;

import org.apache.camel.ServiceStatus;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.context.ContextProvider;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public abstract class AbstractConnection implements NERVConnection {

	private final ContextProvider contextProvider;

	protected final Publisher publisher;

	protected final SubscriptionHandler subscriptionHandler;

	public AbstractConnection(ContextProvider contextProvider, Publisher publisher, SubscriptionHandler subscriptionHandler) {
		super();
		try {
			ServiceStatus status = contextProvider.context().getStatus();
			if (!status.isStarted() && !status.isStarting()) {
				contextProvider.context().start();
			}
		} catch (Exception e) {
			throw new NERVException("Cannot start Camel context.", e);
		}
		this.contextProvider = contextProvider;
		this.publisher = publisher;
		this.subscriptionHandler = subscriptionHandler;
	}

	@Override
	public void publish(String type, Object body) {
		publisher.publish(type, body);
	}

	@Override
	public void publish(Map<String, Object> headers, Object body) {
		publisher.publish(headers, body);
	}

	@Override
	public void publish(Event event) {
		publisher.publish(event);
	}

	@Override
	public void subscribe(Subscription subscription) throws NERVException {
		subscriptionHandler.subscribe(subscription);
	}

	@Override
	public void unsubscribe(Subscription subscription) throws NERVException {
		subscriptionHandler.unsubscribe(subscription);
	}

	@Override
	public void close() throws NERVException {
		try {
			contextProvider.context().stop();
		} catch (Exception e) {
			throw new NERVException("Cannot stop Camel context.", e);
		}
	}

}
