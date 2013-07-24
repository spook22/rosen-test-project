package com.softwareag.eda.nerv.connection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.softwareag.eda.nerv.TestHelper;
import com.softwareag.eda.nerv.channel.StaticChannelProvider;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class VMConnectionUnitTest {

	@Test
	public void testChannelPublishSubscribe() throws Exception {
		String channel = "vm:myChannel";
		String type = "myType";
		String body = "testBody";
		VMConnection connection = new VMConnection(new StaticChannelProvider(channel));

		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(channel, consumer);
		connection.subscribe(subscription);
		try {
			int eventsCount = 2;
			for (int i = 0; i < eventsCount; i++) {
				connection.publish(type, body);
			}
			TestHelper.waitForEvents(consumer, eventsCount, 5000);
			assertEquals(eventsCount, consumer.getEvents().size());
			for (int i = 0; i < eventsCount; i++) {
				assertEquals(body, consumer.getEvents().get(i).getBody());
			}
		} finally {
			connection.unsubscribe(subscription);
		}
	}

}
