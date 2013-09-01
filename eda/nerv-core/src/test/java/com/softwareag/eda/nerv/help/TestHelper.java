package com.softwareag.eda.nerv.help;

import static org.junit.Assert.assertEquals;

import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class TestHelper {

	public static void waitForEvents(BasicConsumer consumer, int eventsCount, int timeout) {
		int waitTime = 100;
		int totalWaitTime = 0;
		while (consumer.getEvents().size() < eventsCount && totalWaitTime <= timeout) {
			synchronized (consumer.getLock()) {
				try {
					consumer.getLock().wait(waitTime);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			totalWaitTime += waitTime;
		}
	}

	// FIXME Remove need for channel.
	public static void testConnection(NERVConnection connection, String channel, int eventsCount) throws Exception {
		String type = "myType";
		String body = "testBody";
		if (channel == null) {
			channel = type;
		}
		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(channel, consumer);
		connection.subscribe(subscription);
		try {
			for (int i = 0; i < eventsCount; i++) {
				connection.publish(type, body);
			}
			waitForEvents(consumer, eventsCount, 5000);
			assertEquals(eventsCount, consumer.getEvents().size());
			for (int i = 0; i < eventsCount; i++) {
				assertEquals(body, consumer.getEvents().get(i).getBody());
			}
		} finally {
			connection.unsubscribe(subscription);
		}
	}

}
