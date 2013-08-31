package com.softwareag.eda.nerv.connection;

import java.util.Map;

import org.apache.camel.ServiceStatus;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.component.ComponentResolver;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.EventDecorator;
import com.softwareag.eda.nerv.event.EventIdDecorator;
import com.softwareag.eda.nerv.event.StartHeaderDecorator;
import com.softwareag.eda.nerv.publish.DefaultPublisher;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.DefaultSubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class VMConnection implements NERVConnection {

	private final ContextProvider contextProvider;

	private final Publisher publisher;

	private final SubscriptionHandler subscriptionHandler;

	public VMConnection(ContextProvider contextProvider, ChannelProvider channelProvider,
			ComponentResolver componentResolver) {
		this.contextProvider = contextProvider;
		try {
			ServiceStatus status = contextProvider.context().getStatus();
			if (!status.isStarted() && !status.isStarting()) {
				contextProvider.context().start();
			}
		} catch (Exception e) {
			throw new NERVException("Cannot start Camel context.", e);
		}
		publisher = new DefaultPublisher(contextProvider, channelProvider, componentResolver, createDecorator());
		subscriptionHandler = new DefaultSubscriptionHandler(contextProvider, channelProvider);
	}

	private EventDecorator createDecorator() {
		return new EventIdDecorator(new StartHeaderDecorator());
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
