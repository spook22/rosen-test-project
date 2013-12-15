package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softwareag.eda.nerv.channel.JmsChannelProvider;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/nerv-jms-context.xml",
		"classpath:/META-INF/spring/nerv-jms-test-context.xml" })
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

	@Resource
	private Component jmsComponent;

	private NERVConnection jmsConnection;

	private final BasicConsumer jmsConsumer = new BasicConsumer();

	private final Subscription jmsSubscription = new DefaultSubscription(type, jmsConsumer);

	@Before
	public void before() {
		CamelContext context = nerv.getContextProvider().context();
		context.addComponent(JMS_COMPONENT_NAME, jmsComponent);
		jmsConnection = nerv.createChannelConnection(jmsChannelProvider.channel(type));
	}

	@Test
	public void test() throws Exception {
		jmsConnection.subscribe(jmsSubscription);
		try {
			Event sentEvent = new Event(type, body);
			nervConnection.publish(sentEvent);
			validateJmsEvent();
		} finally {
			jmsConnection.unsubscribe(jmsSubscription);
		}
	}

	private void validateJmsEvent() {
		int eventsCount = 1;
		TestHelper.waitForEvents(jmsConsumer, eventsCount, 10000);
		assertEquals(eventsCount, jmsConsumer.getEvents().size());
		assertEquals(type, jmsConsumer.getEvents().get(0).getType());
		assertTrue(jmsConsumer.getEvents().get(0).getBody() instanceof String);
	}

}
