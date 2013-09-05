package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.NERV;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.PublishNotification;
import com.softwareag.eda.nerv.help.TestHelper;
import com.softwareag.eda.nerv.jms.ConnectionFactoryProvider;
import com.softwareag.eda.nerv.jms.DefaultDestinationResolverProvider;
import com.softwareag.eda.nerv.jms.JmsComponentCreator;
import com.softwareag.eda.nerv.jms.JmsRouteCreator;
import com.softwareag.eda.nerv.jms.UniversalMessagingConnectionFactoryProvider;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class JmsTest {

	private static final Logger logger = LoggerFactory.getLogger(JmsTest.class);
	
	private static final String JMS_COMPONENT_NAME = "nervDefaultJms";
	
	@Before
	public void before() {
		CamelContext context = NERV.instance().getContextProvider().context();
		ConnectionFactoryProvider connectionFactoryProvider = new UniversalMessagingConnectionFactoryProvider();
		JmsComponentCreator componentCreator = new JmsComponentCreator(connectionFactoryProvider, new DefaultDestinationResolverProvider());
		Component component = componentCreator.createComponent("nsp://localhost:9000", null);
		context.addComponent(JMS_COMPONENT_NAME, component);
	}

	@Test
	public void test() {
		NERVConnection connection = NERV.instance().getDefaultConnection();
		BasicConsumer consumer = new JmsRouteConsumer();
		String expectedType = PublishNotification.TYPE;
		Subscription subscription = new DefaultSubscription(expectedType, consumer);
		connection.subscribe(subscription);
		try {
			Event sentEvent = new Event("JmsTest", "JmsTestBody");
			connection.publish(sentEvent);
			int eventsCount = 1;
			TestHelper.waitForEvents(consumer, eventsCount, 1000);
			assertEquals(eventsCount, consumer.getEvents().size());
			assertEquals(expectedType, consumer.getEvents().get(0).getType());
			assertTrue(consumer.getEvents().get(0).getBody() instanceof PublishNotification);
			PublishNotification notification = (PublishNotification) consumer.getEvents().get(0).getBody();
			assertEquals(sentEvent, notification.getEvent());
		} finally {
			connection.unsubscribe(subscription);
		}
	}

	private static class JmsRouteConsumer extends BasicConsumer {

		private JmsRouteCreator routeCreator = new JmsRouteCreator(NERV.instance().getContextProvider(), new DefaultComponentNameProvider(JMS_COMPONENT_NAME));

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
