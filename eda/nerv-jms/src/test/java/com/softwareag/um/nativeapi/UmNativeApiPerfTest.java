package com.softwareag.um.nativeapi;

import static com.softwareag.um.jms.UmJmsPerfTest.COUNT;
import static com.softwareag.um.jms.UmJmsPerfTest.WAIT;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nChannelNotFoundException;
import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

public class UmNativeApiPerfTest {

	protected nSession session;

	protected nChannel channel;

	protected nConsumeEvent event;

	@Before
	public void before() throws Exception {
		String[] RNAME = { "nsp://127.0.0.1:9000" };
		nSessionAttributes sessionAttributes = new nSessionAttributes(RNAME);
		session = nSessionFactory.create(sessionAttributes);
		session.init();

		nChannelAttributes channelAttributes = new nChannelAttributes(this.getClass().getSimpleName());
		try {
			session.deleteChannel(channelAttributes);
		} catch (nChannelNotFoundException e) {
			// Ignore.
		}
		channelAttributes.setTTL(10000);
		channelAttributes.setType(nChannelAttributes.MIXED_TYPE);
		channel = session.createChannel(channelAttributes);

		event = new nConsumeEvent("TAG", "Test".getBytes());
	}

	@After
	public void after() throws Exception {
		if (session != null) {
			session.close();
		}
	}

	@Test
	public void testNativePersistence() throws Exception {
		EventListener listener = new EventListener(COUNT);
		channel.addSubscriber(listener);
		for (int i = 0; i < COUNT; i++) {
			channel.publish(event);
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
	public void testNativeNoPersistence() throws Exception {
		EventListener listener = new EventListener(COUNT);
		channel.addSubscriber(listener);
		event.setPersistant(false);
		for (int i = 0; i < COUNT; i++) {
			channel.publish(event);
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
