package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.subscription.MulticastChannelSubscription;

public class MulticastSubscriptionHandler extends AbstractSubscriptionHandler<MulticastChannelSubscription> {

	public MulticastSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		super(contextProvider, channelProvider);
	}

	private boolean removeRoute(String id) throws Exception {
		context().stopRoute(id, 30, TimeUnit.SECONDS);
		return context().removeRoute(id);
	}

	@Override
	public void unsubscribe(String type, Consumer consumer) throws Exception {
		MulticastChannelSubscription subscription = findSubscription(channel(type), consumer);
		if (subscription != null) {
			removeRoute(subscription.getId());
			subscription.removeProcessor(new DefaultEventProcessor(consumer));
			if (subscription.isEmpty()) {
				removeSubscription(subscription);
			} else {
				context().addRoutes(subscription);
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
