package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.Header;
import com.softwareag.eda.nerv.help.SystemPropertyChanger;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class NERVUnitTest extends AbstractNERVUnitTest {

	@Test
	public void testPubSub100kMsgs() throws Exception {
		pubSub(100000);
	}

	@Test
	public void testPubSub100kMsgs10Threads() throws Exception {
		pubSub(100000, 1, 10);
	}

	@Test
	public void testPublishWithEvent() throws Exception {
		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(type, consumer);
		connection.subscribe(subscription);
		connection.publish(new Event(type, body));

		if (consumer.getEvents().size() < 1) {
			synchronized (consumer.getLock()) {
				consumer.getLock().wait(1000);
			}
		}
		assertEquals(1, consumer.getEvents().size());
		assertEquals(body, consumer.getEvents().get(0).getBody());
		connection.unsubscribe(subscription);
	}

	@Test
	public void testPublishWithHeadersAndBody() throws Exception {
		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(type, consumer);
		connection.subscribe(subscription);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(Header.TYPE.getName(), type);
		connection.publish(headers, body);

		if (consumer.getEvents().size() < 1) {
			synchronized (consumer.getLock()) {
				consumer.getLock().wait(1000);
			}
		}
		assertEquals(1, consumer.getEvents().size());
		assertEquals(body, consumer.getEvents().get(0).getBody());
		connection.unsubscribe(subscription);
	}

	private static SystemPropertyChanger propertyChanger = new SystemPropertyChanger(NERV.PROP_CREATE_DEFAULT_CONNECTION);

	@Test
	public void testGetDefaultConnection() throws Exception {
		propertyChanger.change(Boolean.TRUE.toString());
		try {
			NERVConnection connection = NERV.instance().getDefaultConnection();
			assertNotNull(connection);
			TestHelper.testConnection(connection, null, 5);
			connection = NERV.instance().getDefaultConnection();
			assertNotNull(connection);
			TestHelper.testConnection(connection, null, 5);
		} finally {
			propertyChanger.revert();
		}
	}

	@Test(expected = NERVException.class)
	public void testGetDefaultConnectionNotInitialized() throws Exception {
		propertyChanger.change(Boolean.FALSE.toString());
		try {
			NERV.instance().destroyDefaultConnection();
			NERV.instance().getDefaultConnection();
		} finally {
			propertyChanger.revert();
		}
	}

	@Test(expected = NERVException.class)
	public void testSetDefaultConnectionAlreadySet() {
		NERV.instance().getDefaultConnection();
		NERV.instance().setDefaultConnection(null);
	}

	@Test(expected = NERVException.class)
	public void testDestroyDefaultConnection() {
		propertyChanger.change(Boolean.TRUE.toString());
		try {
			NERVConnection connection = NERV.instance().getDefaultConnection();
			assertNotNull(connection);
		} finally {
			propertyChanger.revert();
		}
		propertyChanger.change(Boolean.FALSE.toString());
		try {
			NERV.instance().destroyDefaultConnection();
			NERV.instance().getDefaultConnection();
		} finally {
			propertyChanger.revert();
		}
	}

	@Test
	public void testCreateChannelConnection() throws Exception {
		String channel = "vm:testCreateChannelConnection";
		NERVConnection connection = NERV.instance().createChannelConnection(channel);
		TestHelper.testConnection(connection, channel, 5);
	}

}