package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softwareag.eda.nerv.channel.JmsChannelProvider;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.PublishNotification;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.jms.JmsComponentCreator;
import com.softwareag.eda.nerv.jms.route.JmsRouteCreator;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/beans.xml", "classpath:/META-INF/spring/jms-beans.xml", "classpath:/META-INF/spring/nerv-jms-test-context.xml" })
public class JmsTest {

	private static final Logger logger = LoggerFactory.getLogger(JmsTest.class);
	
	private static final String JMS_COMPONENT_NAME = "nervDefaultJms";
	
	private final ComponentNameProvider componentNameProvider = new DefaultComponentNameProvider(JMS_COMPONENT_NAME);
	
	private final JmsChannelProvider jmsChannelProvider = new JmsChannelProvider(componentNameProvider);
	
	@Resource
	private String type;
	
	private final String body = "JmsTestBody";
	
	@Resource
	private NERV nerv;
	
	@Resource
	private JmsComponentCreator componentCreator;
	
	private NERVConnection jmsConnection;
	
	@Resource
	private NERVConnection nervConnection;

	@Resource
	private BasicConsumer jmsConsumer;
	
	@Resource
	private Subscription jmsSubscription;
	
	@Resource
	private Component jmsComponent;
	
	@Before
	public void before() {
		CamelContext context = nerv.getContextProvider().context();
		context.addComponent(JMS_COMPONENT_NAME, jmsComponent);
		jmsConnection = nerv.createChannelConnection(jmsChannelProvider.channel(type));
	}

	@Test
	public void test() throws Exception {
		BasicConsumer consumer = new PublishNotificationConsumer();
		String expectedType = PublishNotification.TYPE;
		Subscription subscription = new DefaultSubscription(expectedType, consumer);
		nervConnection.subscribe(subscription);
		jmsConnection.subscribe(jmsSubscription);
		try {
			Event sentEvent = new Event(type, body);
			nervConnection.publish(sentEvent);
			validatePublishNotificationEvent(consumer, expectedType, sentEvent);
			validateJmsEvent();
		} finally {
			nervConnection.unsubscribe(subscription);
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

	private void validatePublishNotificationEvent(BasicConsumer consumer, String expectedType, Event sentEvent) {
		int eventsCount = 1;
		TestHelper.waitForEvents(consumer, eventsCount, 1000);
		assertEquals(eventsCount, consumer.getEvents().size());
		assertEquals(expectedType, consumer.getEvents().get(0).getType());
		assertTrue(consumer.getEvents().get(0).getBody() instanceof PublishNotification);
		PublishNotification notification = (PublishNotification) consumer.getEvents().get(0).getBody();
		assertEquals(sentEvent, notification.getEvent());
	}

	private class PublishNotificationConsumer extends BasicConsumer {

		private JmsRouteCreator routeCreator = new JmsRouteCreator(NERV.instance().getContextProvider(), componentNameProvider);

		@Override
		public void consume(Event event) {
			super.consume(event);
			logger.debug("Received event: " + event.toString());
			PublishNotification notification = (PublishNotification) event.getBody();
			try {
				routeCreator.onPublish(notification.getOperation(), notification.getChannel(), notification.getEvent());
			} catch (Exception e) {
				logger.error(
						"Cannot process notification. Most probably JMS route creation failed for published event.", e);
			}
		}
	}

}
