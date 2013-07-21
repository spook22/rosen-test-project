package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.subscription.MulticastChannelSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class MulticastSubscriptionHandler extends AbstractSubscriptionHandler<MulticastChannelSubscription> {

	public MulticastSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		super(contextProvider, channelProvider);
	}

	private boolean removeRoute(String id) throws Exception {
		context().stopRoute(id, 30, TimeUnit.SECONDS);
		return context().removeRoute(id);
	}

	@Override
	public void unsubscribe(Subscription subscription) throws Exception {
		MulticastChannelSubscription route = findSubscription(channel(subscription.channel()), subscription.consumer());
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
	protected MulticastChannelSubscription findSubscription(String channel, Consumer consumer) {
		Set<MulticastChannelSubscription> channelSubscriptions = subscriptions.get(channel);
		if (channelSubscriptions != null) {
			for (MulticastChannelSubscription channelSubscription : channelSubscriptions) {
				if (channelSubscription.getChannel().equals(channel)) {
					return channelSubscription;
				}
			}
		}
		return null;
	}

	@Override
	protected MulticastChannelSubscription createSubscripion(String channel, Consumer consumer) {
		return new MulticastChannelSubscription(channel, new DefaultEventProcessor(consumer));
	}

	@Override
	protected void processExistingSubscription(MulticastChannelSubscription subscription, Consumer consumer) throws Exception {
		removeRoute(subscription.getId());
		subscription.addProcessor(new DefaultEventProcessor(consumer));
	}

}
