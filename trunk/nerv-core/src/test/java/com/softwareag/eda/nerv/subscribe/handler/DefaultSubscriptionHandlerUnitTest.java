package com.softwareag.eda.nerv.subscribe.handler;

import static org.junit.Assert.assertEquals;

import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.SimpleContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.channel.VMChannelProvider;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.publish.DefaultPublisher;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class DefaultSubscriptionHandlerUnitTest {

	private final String type = "DefaultSubscriptionHandlerUnitTest";

	private final String body = "testBody";

	@Test
	public void testSubscribeUnsubscribe() throws Exception {
		ContextProvider contextProvider = new SimpleContextProvider(new DefaultCamelContext());
		contextProvider.context().start();
		ChannelProvider channelProvider = new VMChannelProvider();

		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(type, consumer);

		DefaultSubscriptionHandler subscriptionHandler = new DefaultSubscriptionHandler(contextProvider, channelProvider);
		subscriptionHandler.subscribe(subscription);

		Publisher publisher = new DefaultPublisher(contextProvider, channelProvider);
		publisher.publish(type, body);

		if (consumer.getEvents().size() < 1) {
			synchronized (consumer.getLock()) {
				consumer.getLock().wait(1000);
			}
		}

		assertEquals(1, consumer.getEvents().size());
		assertEquals(body, consumer.getEvents().get(0).getBody());

		// Try to unsubscribe from a non-existing subscription.
		subscriptionHandler.unsubscribe(new DefaultSubscription(type, new BasicConsumer()));

		subscriptionHandler.unsubscribe(subscription);

		// Try to unsubscribe from a non-existing subscription.
		subscriptionHandler.unsubscribe(new DefaultSubscription(type, new BasicConsumer()));
	}

}
