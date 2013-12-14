package com.softwareag.eda.nerv.direct;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/beans.xml", "classpath:/META-INF/spring/camel-context.xml" })
public class DirectTest {

	private final String type = "DirectTestType";

	private final String body = "DirectTestBody";

	@Resource
	private NERVConnection connection;

	@Test
	public void test() {
		BasicConsumer consumer = new BasicConsumer();
		connection.subscribe(new DefaultSubscription(type, consumer));
		try {
			int expectedMessages = 1;
			connection.publish(type, body);
			TestHelper.waitForEvents(consumer, expectedMessages, 1000);
			assertEquals(expectedMessages, consumer.getEvents().size());
			assertEquals(body, consumer.getEvents().get(0).getBody());
		} finally {
			connection.unsubscribe(new DefaultSubscription(type, consumer));
		}
	}
}
