package com.softwareag.um.jms;

import static org.junit.Assert.assertEquals;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pcbsys.nirvana.nJMS.TopicConnectionFactoryImpl;

public class UmJmsPerfTest {

	public static final int COUNT = 1000000;

	public static final int WAIT = 60000;

	protected ConnectionFactory factory = new TopicConnectionFactoryImpl("nsp://localhost:9000");

	protected Connection connection;

	protected Session session;

	protected Destination destination;

	protected MessageProducer producer;

	protected MessageConsumer consumer;

	protected TextMessage message;

	@Before
	public void before() throws Exception {
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createTopic(this.getClass().getSimpleName());
		producer = session.createProducer(destination);
		producer.setTimeToLive(10000);
		consumer = session.createConsumer(destination);
		message = session.createTextMessage("Test");

		warmup();
	}

	private void warmup() throws Exception {
		// Send 10k messages to warmup UM.
		for (int i = 0; i < 1000; i++) {
			producer.send(message);
		}
		Thread.sleep(2000);
	}

	@After
	public void after() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}

	@Test
	public void testJmsPersistence() throws Exception {
		JmsMessageListener listener = new JmsMessageListener(COUNT);
		consumer.setMessageListener(listener);
		for (int i = 0; i < COUNT; i++) {
			producer.send(message);
		}

		int receivedEvents = listener.getReceivedEvents();
		if (receivedEvents < COUNT) {
			synchronized (listener.getLock()) {
				listener.getLock().wait(WAIT);
			}
		}
		assertEquals(COUNT, listener.getReceivedEvents());
	}

	@Test
	public void testJmsNoPersistence() throws Exception {
		JmsMessageListener listener = new JmsMessageListener(COUNT);
		consumer.setMessageListener(listener);
		message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i < COUNT; i++) {
			producer.send(message);
		}

		int receivedEvents = listener.getReceivedEvents();
		if (receivedEvents < COUNT) {
			synchronized (listener.getLock()) {
				listener.getLock().wait(WAIT);
			}
		}
		assertEquals(COUNT, listener.getReceivedEvents());
	}

}
