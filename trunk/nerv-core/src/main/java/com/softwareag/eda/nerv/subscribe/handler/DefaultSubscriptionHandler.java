package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultChannelSubscription;

public class DefaultSubscriptionHandler extends AbstractSubscriptionHandler<DefaultChannelSubscription> {

	public DefaultSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		super(contextProvider, channelProvider);
	}

	private boolean removeRoute(String id) throws Exception {
		context().stopRoute(id, 30, TimeUnit.SECONDS);
		return context().removeRoute(id);
	}

	@Override
	public void unsubscribe(String type, Consumer consumer) throws Exception {
		DefaultChannelSubscription subscription = findSubscription(channel(type), consumer);
		if (subscription != null) {
			removeRoute(subscription.getId());
			removeSubscription(subscription);
		}
	}

	@Override
	protected DefaultChannelSubscription findSubscription(String channel, Consumer consumer) {
		Set<DefaultChannelSubscription> channelSubscriptions = subscriptions.get(channel);
		if (channelSubscriptions != null) {
			for (DefaultChannelSubscription channelSubscription : channelSubscriptions) {
				if (channelSubscription.getProcessor().equals(new DefaultEventProcessor(consumer))) {
					return channelSubscription;
				}
			}
		}
		return null;
	}

	@Override
	protected DefaultChannelSubscription createSubscripion(String channel, Consumer consumer) {
		return new DefaultChannelSubscription(channel, new DefaultEventProcessor(consumer));
	}

	@Override
	protected void processExistingSubscription(DefaultChannelSubscription subscription, Consumer consumer) throws Exception {
		// Nothing to do in this case.
	}

}