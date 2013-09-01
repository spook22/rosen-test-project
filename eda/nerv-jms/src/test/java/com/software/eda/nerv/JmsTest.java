package com.software.eda.nerv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.NERV;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class JmsTest {

	private static final Logger logger = LoggerFactory.getLogger(JmsTest.class);

	@Test
	public void test() {
		NERVConnection connection = NERV.instance().getDefaultConnection();
		BasicConsumer consumer = new JmsRouteConsumer();
		String expectedType = "EventPublished";
		Subscription subscription = new DefaultSubscription(expectedType, consumer);
		connection.subscribe(subscription);
		try {
			Event sentEvent = new Event("JmsTest", "JmsTestBody");
			connection.publish(sentEvent);
			int eventsCount = 1;
			TestHelper.waitForEvents(consumer, eventsCount, 1000);
			assertEquals(eventsCount, consumer.getEvents().size());
			assertEquals(expectedType, consumer.getEvents().get(0).getType());
			assertEquals(sentEvent, consumer.getEvents().get(0).getBody());
		} finally {
			connection.unsubscribe(subscription);
		}
	}

	private static class JmsRouteConsumer extends BasicConsumer {

		@Override
		public void consume(Event event) {
			super.consume(event);
			logger.info(event.toString());
		}

	}

}
