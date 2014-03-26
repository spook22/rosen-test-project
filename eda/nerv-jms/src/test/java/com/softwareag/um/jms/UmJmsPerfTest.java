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
import com.softwareag.Measurement;
import com.softwareag.Utils;

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

	protected JmsMessageListener listener;

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
	}

	@After
	public void after() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}

	@Test
	public void testJmsPersistence() throws Exception {
		configure();
		long start = System.currentTimeMillis();
		send();
		waitForConsumption();
		validate();
		record("testJmsPersistence", System.currentTimeMillis() - start);
	}

	@Test
	public void testJmsNoPersistence() throws Exception {
		configure();
		message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
		long start = System.currentTimeMillis();
		send();
		waitForConsumption();
		validate();
		record("testJmsNoPersistence", System.currentTimeMillis() - start);
	}

	private void configure() throws Exception {
		listener = new JmsMessageListener(COUNT);
		consumer.setMessageListener(listener);
	}

	private void send() throws Exception {
		for (int i = 0; i < COUNT; i++) {
			producer.send(message);
		}
	}

	private void waitForConsumption() throws Exception {
		int receivedEvents = listener.getReceivedEvents();
		if (receivedEvents < COUNT) {
			synchronized (listener.getLock()) {
				listener.getLock().wait(WAIT);
			}
		}
	}

	private void validate() {
		assertEquals(COUNT, listener.getReceivedEvents());
	}

	private void record(String testName, long milliseconds) {
		Measurement measurement = new Measurement(System.currentTimeMillis(), testName, COUNT, milliseconds);
		Utils.record(measurement);
	}

}
