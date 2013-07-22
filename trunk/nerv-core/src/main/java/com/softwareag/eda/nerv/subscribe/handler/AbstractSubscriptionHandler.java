package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.camel.CamelContext;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.subscribe.subscription.AbstractChannelRoute;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public abstract class AbstractSubscriptionHandler<T extends AbstractChannelRoute> implements SubscriptionHandler {

	protected final Map<String, Set<T>> subscriptions = Collections.synchronizedMap(new HashMap<String, Set<T>>());

	private final ContextProvider contextProvider;

	private final ChannelProvider channelProvider;

	public AbstractSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		this.contextProvider = contextProvider;
		this.channelProvider = channelProvider;
	}

	protected CamelContext context() {
		return contextProvider.context();
	}

	protected String channel(String type) {
		return channelProvider.channel(type);
	}

	protected void addSubscription(T subscription) {
		Set<T> channelSubscriptions = subscriptions.get(subscription.getChannel());
		if (channelSubscriptions == null) {
			channelSubscriptions = Collections.synchronizedSet(new HashSet<T>());
			subscriptions.put(subscription.getChannel(), channelSubscriptions);
		}
		channelSubscriptions.add(subscription);
	}

	protected void removeSubscription(AbstractChannelRoute subscription) {
		Set<T> channelSubscriptions = subscriptions.get(subscription.getChannel());
		if (channelSubscriptions != null) {
			channelSubscriptions.remove(subscription);
		}
	}

	@Override
	public void subscribe(Subscription subscription) throws Exception {
		String channel = channel(subscription.channel());
		Consumer consumer = subscription.consumer();
		T route = findRoute(channel, consumer);
		if (route == null) {
			route = createSubscripion(channel, consumer);
			addSubscription(route);
		} else {
			processExistingSubscription(route, consumer);
		}
		context().addRoutes(route);
	}

	protected abstract T findRoute(String channel, Consumer consumer);

	protected abstract T createSubscripion(String channel, Consumer consumer);

	protected abstract void processExistingSubscription(T subscription, Consumer consumer) throws Exception;

}
