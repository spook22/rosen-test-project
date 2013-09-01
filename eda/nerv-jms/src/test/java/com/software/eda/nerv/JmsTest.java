package com.software.eda.nerv;

import org.junit.Test;

import com.softwareag.eda.nerv.NERV;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class JmsTest {

	@Test
	public void test() {
		NERVConnection connection = NERV.instance().getDefaultConnection();
		connection.publish(new Event("JmsTest", "JmsTestBody"));
		Subscription subscription = new DefaultSubscription("EventPublished", new JmsRouteConsumer());
		connection.subscribe(subscription);
	}

	private static class JmsRouteConsumer implements Consumer {

		@Override
		public void consume(Event event) {
			System.out.println(event);
		}

	}

}
