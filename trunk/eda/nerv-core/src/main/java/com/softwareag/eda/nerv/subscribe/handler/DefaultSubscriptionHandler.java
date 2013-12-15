package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Set;

import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.context.ContextProvider;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.route.DefaultSubscriptionRoute;

public class DefaultSubscriptionHandler extends AbstractSubscriptionHandler<DefaultSubscriptionRoute> {

	public DefaultSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		super(contextProvider, channelProvider);
	}

	@Override
	protected DefaultSubscriptionRoute findRoute(String channel, Consumer consumer) {
		Set<DefaultSubscriptionRoute> channelSubscriptions = subscriptions.get(channel);
		if (channelSubscriptions != null) {
			for (DefaultSubscriptionRoute channelSubscription : channelSubscriptions) {
				if (channelSubscription.getProcessor().equals(new DefaultEventProcessor(consumer))) {
					return channelSubscription;
				}
			}
		}
		return null;
	}

	@Override
	protected DefaultSubscriptionRoute createSubscripion(String channel, Consumer consumer) {
		return new DefaultSubscriptionRoute(channel, new DefaultEventProcessor(consumer));
	}

}
