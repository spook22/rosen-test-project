package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Set;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.route.DefaultRoute;

public class DefaultSubscriptionHandler extends AbstractSubscriptionHandler<DefaultRoute> {

	public DefaultSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		super(contextProvider, channelProvider);
	}

	@Override
	protected DefaultRoute findRoute(String channel, Consumer consumer) {
		Set<DefaultRoute> channelSubscriptions = subscriptions.get(channel);
		if (channelSubscriptions != null) {
			for (DefaultRoute channelSubscription : channelSubscriptions) {
				if (channelSubscription.getProcessor().equals(new DefaultEventProcessor(consumer))) {
					return channelSubscription;
				}
			}
		}
		return null;
	}

	@Override
	protected DefaultRoute createSubscripion(String channel, Consumer consumer) {
		return new DefaultRoute(channel, new DefaultEventProcessor(consumer));
	}

}
