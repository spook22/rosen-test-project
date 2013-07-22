package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.Header;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NERV.class)
public class NERVUnitTest extends AbstractNERVUnitTest {

	@Test
	public void testPubSub() throws Exception {
		pubSub(1);
	}

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
		NERV.instance().subscribe(subscription);
		NERV.instance().publish(new Event(type, message));

		if (consumer.getEvents().size() < 1) {
			synchronized (consumer.getLock()) {
				consumer.getLock().wait(1000);
			}
		}
		assertEquals(1, consumer.getEvents().size());
		assertEquals(message, consumer.getEvents().get(0).getBody());
		NERV.instance().unsubscribe(subscription);
	}

	@Test
	public void testPublishWithHeadersAndBody() throws Exception {
		BasicConsumer consumer = new BasicConsumer();
		Subscription subscription = new DefaultSubscription(type, consumer);
		NERV.instance().subscribe(subscription);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(Header.TYPE.getName(), type);
		NERV.instance().publish(headers, message);

		if (consumer.getEvents().size() < 1) {
			synchronized (consumer.getLock()) {
				consumer.getLock().wait(1000);
			}
		}
		assertEquals(1, consumer.getEvents().size());
		assertEquals(message, consumer.getEvents().get(0).getBody());
		NERV.instance().unsubscribe(subscription);
	}

	// @Test(expected = NERVException.class)
	// public void testGetInstanceCannotStartContext() throws Exception {
	// DefaultCamelContext context = createMock(DefaultCamelContext.class);
	// expectNew(DefaultCamelContext.class).andReturn(context);
	// replay(context, DefaultCamelContext.class);
	// NERV.setInstance(null);
	// NERV.instance();
	// verify(context, DefaultCamelContext.class);
	//
	// }

}
