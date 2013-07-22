package com.softwareag.eda.nerv.publish;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.SimpleContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.channel.VMChannelProvider;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.EventIdDecorator;
import com.softwareag.eda.nerv.event.Header;
import com.softwareag.eda.nerv.subscribe.handler.DefaultSubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class DefaultPublisherUnitTest {

	private ContextProvider contextProvider;

	private final ChannelProvider channelProvider = new VMChannelProvider();

	private final String type = "DefaultPublisherUnitTest";

	private final String body = "testBody";

	private BasicConsumer consumer;

	private Subscription subscription;

	private SubscriptionHandler subscriptionHandler;

	private DefaultPublisher publisher;

	@Before
	public void setUp() throws Exception {
		contextProvider = new SimpleContextProvider(new DefaultCamelContext());
		contextProvider.context().start();

		consumer = new BasicConsumer();
		subscription = new DefaultSubscription(type, consumer);

		subscriptionHandler = new DefaultSubscriptionHandler(contextProvider, channelProvider);
		subscriptionHandler.subscribe(subscription);

		publisher = new DefaultPublisher(contextProvider, channelProvider);
	}

	@After
	public void tearDown() throws Exception {
		contextProvider.context().stop();
		subscriptionHandler.unsubscribe(subscription);
	}

	@Test
	public void testPublishUsingTypeAndBody() throws Exception {
		publisher.publish(type, body);
		publisher.publish(type, body);
		waitForEvents(2);
		assertEquals(2, consumer.getEvents().size());
		assertEquals(body, consumer.getEvents().get(0).getBody());
		assertEquals(body, consumer.getEvents().get(1).getBody());
	}

	@Test
	public void testPublishUsingHeadersMap() throws Exception {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(Header.TYPE.getName(), type);
		publisher.publish(headers, body);
		waitForEvents(1);
		assertEquals(1, consumer.getEvents().size());
		assertEquals(body, consumer.getEvents().get(0).getBody());
	}

	@Test
	public void testPublishEvent() throws Exception {
		Event event = new Event(type, body);
		assertNull(event.getHeader(Header.EVENT_ID));
		publisher.setDecorator(new EventIdDecorator());
		publisher.publish(event);
		waitForEvents(1);
		assertEquals(1, consumer.getEvents().size());
		assertEquals(body, consumer.getEvents().get(0).getBody());
		assertNotNull(consumer.getEvents().get(0).getHeader(Header.EVENT_ID));
	}

	private void waitForEvents(int eventsCount) throws Exception {
		if (consumer.getEvents().size() < eventsCount) {
			synchronized (consumer.getLock()) {
				consumer.getLock().wait(2000);
			}
		}
	}

}
