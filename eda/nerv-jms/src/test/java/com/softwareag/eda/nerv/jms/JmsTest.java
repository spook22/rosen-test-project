package com.softwareag.eda.nerv.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softwareag.eda.nerv.NERV;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.jms.channel.JmsChannelProvider;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JmsTest {

	private static final String JMS_COMPONENT_NAME = "nervDefaultJms";

	private final ComponentNameProvider componentNameProvider = new DefaultComponentNameProvider(JMS_COMPONENT_NAME);

	private final JmsChannelProvider jmsChannelProvider = new JmsChannelProvider(componentNameProvider);

	private final String type = "JmsTestType";

	private final String body = "JmsTestBody";

	@Resource
	private NERV nerv;

	@Resource
	private NERVConnection nervConnection;

	private NERVConnection jmsConnection;

	private final int eventsCount = 20000;

	private final BasicConsumer jmsConsumer = new BasicConsumer(eventsCount);

	private final Subscription jmsSubscription = new DefaultSubscription(type, jmsConsumer);

	@Before
	public void before() {
		jmsConnection = nerv.createChannelConnection(jmsChannelProvider.channel(type));
	}

	@Test
	public void test() throws Exception {
		jmsConnection.subscribe(jmsSubscription);
		try {
			for (int i = 0; i < eventsCount; i++) {
				Event sentEvent = new Event(type, body);
				nervConnection.publish(sentEvent);
			}
			validateJmsEvents(eventsCount);
		} finally {
			jmsConnection.unsubscribe(jmsSubscription);
		}
	}

	private void validateJmsEvents(int eventsCount) {
		TestHelper.waitForEvents(jmsConsumer, eventsCount, 20000);
		assertEquals(eventsCount, jmsConsumer.getEvents().size());
		assertEquals(type, jmsConsumer.getEvents().get(0).getType());
		assertTrue(jmsConsumer.getEvents().get(0).getBody() instanceof String);
	}

}
