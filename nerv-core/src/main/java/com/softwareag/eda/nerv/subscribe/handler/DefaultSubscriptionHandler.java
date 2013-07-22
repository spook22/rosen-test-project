package com.softwareag.eda.nerv.subscribe.handler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.route.DefaultRoute;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class DefaultSubscriptionHandler extends AbstractSubscriptionHandler<DefaultRoute> {

	public DefaultSubscriptionHandler(ContextProvider contextProvider, ChannelProvider channelProvider) {
		super(contextProvider, channelProvider);
	}

	private boolean removeRoute(String id) throws Exception {
		context().stopRoute(id, 30, TimeUnit.SECONDS);
		return context().removeRoute(id);
	}

	@Override
	public void unsubscribe(Subscription subscription) throws Exception {
		DefaultRoute route = findRoute(channel(subscription.channel()), subscription.consumer());
		if (route != null) {
			removeRoute(route.getId());
			removeSubscription(route);
		}
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

	@Override
	protected void processExistingSubscription(DefaultRoute subscription, Consumer consumer) throws Exception {
		// Nothing to do in this case.
	}

}
