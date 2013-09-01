package com.software.eda.nerv;

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
		Subscription subscription = new DefaultSubscription("EventPublished", consumer);
		connection.subscribe(subscription);
		connection.publish(new Event("JmsTest", "JmsTestBody"));
		TestHelper.waitForEvents(consumer, 1, 1000);
	}

	private static class JmsRouteConsumer extends BasicConsumer {

		@Override
		public void consume(Event event) {
			super.consume(event);
			logger.info(event.toString());
		}

	}

}
