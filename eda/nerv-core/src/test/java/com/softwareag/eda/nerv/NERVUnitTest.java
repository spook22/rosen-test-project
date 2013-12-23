package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.Header;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class NERVUnitTest extends AbstractNERVUnitTest {

	@Test
	public void testPubSub10kMsgs2Consumers() throws Exception {
		pubSub(10000, 2, 1);
	}

	@Test
	public void testPublishWithEvent() throws Exception {
		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(type, consumer);
		connection.subscribe(subscription);
		try {
			connection.publish(new Event(type, body));
			int eventsCount = 1;
			TestHelper.waitForEvents(consumer, eventsCount, 1000);
			assertEquals(eventsCount, consumer.getEvents().size());
			assertEquals(body, consumer.getEvents().get(0).getBody());
		} finally {
			connection.unsubscribe(subscription);
		}
	}

	@Test
	public void testPublishWithHeadersAndBody() throws Exception {
		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(type, consumer);
		connection.subscribe(subscription);
		try {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put(Header.TYPE.getName(), type);
			connection.publish(headers, body);
			TestHelper.waitForEvents(consumer, 1, 1000);
			assertEquals(1, consumer.getEvents().size());
			assertEquals(body, consumer.getEvents().get(0).getBody());
		} finally {
			connection.unsubscribe(subscription);
		}
	}

	@Test
	public void testGetDefaultConnection() throws Exception {
		NERVConnection connection = NERV.instance().getDefaultConnection();
		assertNotNull(connection);
		TestHelper.testConnection(connection, null, 5);
		connection = NERV.instance().getDefaultConnection();
		assertNotNull(connection);
		TestHelper.testConnection(connection, null, 5);
	}

	@Test(expected = NERVException.class)
	public void testSetDefaultConnectionAlreadySet() {
		NERV.instance().getDefaultConnection();
		NERV.instance().setDefaultConnection(null);
	}

	public void testDestroyDefaultConnection() {
		NERVConnection connection = NERV.instance().getDefaultConnection();
		assertNotNull(connection);
		NERV.instance().close();
		assertNull(NERV.instance().defaultConnection);
	}

	@Test
	public void testCreateChannelConnection() throws Exception {
		String channel = "vm:testCreateChannelConnection";
		NERVConnection connection = NERV.instance().createChannelConnection(channel);
		TestHelper.testConnection(connection, channel, 5);
	}

}
