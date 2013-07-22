package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.route.MulticastRoute;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class MulticastSubscriptionHandler extends AbstractSubscriptionHandler<MulticastRoute> {

	public MulticastSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		super(contextProvider, channelProvider);
	}

	private boolean removeRoute(String id) throws Exception {
		context().stopRoute(id, 30, TimeUnit.SECONDS);
		return context().removeRoute(id);
	}

	@Override
	public void unsubscribe(Subscription subscription) throws Exception {
		MulticastRoute route = findRoute(channel(subscription.channel()), subscription.consumer());
		if (route != null) {
			removeRoute(route.getId());
			route.removeProcessor(new DefaultEventProcessor(subscription.consumer()));
			if (route.isEmpty()) {
				removeSubscription(route);
			} else {
				context().addRoutes(route);
			}
		}
	}

	@Override
	protected MulticastRoute findRoute(String channel, Consumer consumer) {
		Set<MulticastRoute> channelSubscriptions = subscriptions.get(channel);
		if (channelSubscriptions != null) {
			for (MulticastRoute channelSubscription : channelSubscriptions) {
				if (channelSubscription.getChannel().equals(channel)) {
					return channelSubscription;
				}
			}
		}
		return null;
	}

	@Override
	protected MulticastRoute createSubscripion(String channel, Consumer consumer) {
		return new MulticastRoute(channel, new DefaultEventProcessor(consumer));
	}

	@Override
	protected void processExistingSubscription(MulticastRoute subscription, Consumer consumer) throws Exception {
		removeRoute(subscription.getId());
		subscription.addProcessor(new DefaultEventProcessor(consumer));
	}

}
