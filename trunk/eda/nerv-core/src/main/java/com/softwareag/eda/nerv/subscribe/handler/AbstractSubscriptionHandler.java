package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.context.ContextProvider;
import com.softwareag.eda.nerv.subscribe.route.AbstractRoute;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public abstract class AbstractSubscriptionHandler<T extends AbstractRoute> implements SubscriptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractSubscriptionHandler.class);

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

	protected void removeSubscription(AbstractRoute subscription) {
		Set<T> channelSubscriptions = subscriptions.get(subscription.getChannel());
		channelSubscriptions.remove(subscription);
		if (channelSubscriptions.isEmpty()) {
			subscriptions.remove(subscription.getChannel());
		}
	}

	protected boolean removeRoute(String id) throws NERVException {
		try {
			context().stopRoute(id, 30, TimeUnit.SECONDS);
			return context().removeRoute(id);
		} catch (Exception e) {
			throw new NERVException("Cannot remove route " + id + " from context.", e);
		}
	}

	@Override
	public void subscribe(Subscription subscription) throws NERVException {
		String channel = channel(subscription.type());
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Subscribing for event type %s using channel %s.", subscription.type(), channel));
		}
		Consumer consumer = subscription.consumer();
		T route = findRoute(channel, consumer);
		if (route == null) {
			if (logger.isInfoEnabled()) {
				logger.info(String.format("Creating subscription for channel %s.", channel));
			}
			route = createSubscripion(channel, consumer);
			addSubscription(route);
			try {
				context().addRoutes(route);
			} catch (Exception e) {
				throw new NERVException("Cannot add route to context.", e);
			}
		}
	}

	@Override
	public void unsubscribe(Subscription subscription) throws NERVException {
		T route = findRoute(channel(subscription.type()), subscription.consumer());
		if (route != null) {
			removeRoute(route.getId());
			removeSubscription(route);
		}
	}

	protected abstract T findRoute(String channel, Consumer consumer);

	protected abstract T createSubscripion(String channel, Consumer consumer);

}
