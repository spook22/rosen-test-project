package com.softwareag.um.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pcbsys.nirvana.nJMS.TopicConnectionFactoryImpl;

public class UmJmsPerfTest {

	private static final int msgCount = 1000000;

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
		consumer = session.createConsumer(destination);
		message = session.createTextMessage("Test");

		warmup();
	}

	private void warmup() throws Exception {
		// Send 10k messages to warmup UM.
		for (int i = 0; i < 10000; i++) {
			producer.send(message);
		}
	}

	@After
	public void after() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}

	@Test
	public void testJmsPerfNoConsumer() throws Exception {
		for (int i = 0; i < msgCount; i++) {
			producer.send(message);
		}
	}

	@Test
	public void testJmsPerfNoConsumerNoPersistence() throws Exception {
		message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i < msgCount; i++) {
			producer.send(message);
		}
	}

	@Test
	public void testJmsPerfWithConsumer() throws Exception {
		MessageListener listener = new MessageListener() {
			@Override
			public void onMessage(Message message) {
			}
		};
		consumer.setMessageListener(listener);
		try {
			for (int i = 0; i < msgCount; i++) {
				producer.send(message);
			}
		} finally {
			consumer.close();
		}
	}

}
