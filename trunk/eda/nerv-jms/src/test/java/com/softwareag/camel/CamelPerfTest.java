package com.softwareag.camel;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.UnitOfWorkHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CamelPerfTest extends CamelTestSupport {

	private static final String expectedBody = "testBody";

	@Resource
	private Integer expectedCount;

	@Resource
	private DefaultProcessor jmsProcessor;

	@Resource
	protected ProducerTemplate template;

	protected Producer producer;

	protected Exchange exchange;

	@Before
	public void before() throws Exception {
		producer = context.getEndpoint("vm:testPerformanceSjms").createProducer();
		exchange = createExchange();
		warmup();
	}

	private void warmup() throws Exception {
		for (int i = 0; i < 10; i++) {
			producer.process(exchange);
		}
		Thread.sleep(1000);
		jmsProcessor.clean();
	}

	@Ignore
	@Test
	public void testVmToJmsComponent() throws Exception {
		for (int i = 0; i < expectedCount; i++) {
			producer.process(exchange);
		}

		int receivedEvents = jmsProcessor.getReceivedMessages();
		if (receivedEvents < expectedCount) {
			synchronized (jmsProcessor.getLock()) {
				jmsProcessor.getLock().wait(60000);
			}
		}
		assertEquals(expectedCount.intValue(), jmsProcessor.getReceivedMessages());
	}

	@Test
	public void testVmToSjmsComponent() throws Exception {
		log.info("Expected count set to " + expectedCount);
		template.setDefaultEndpointUri("sjms:topic:testPerformanceSjms?producerCount=10&ttl=1000&synchronous=false");
		Map<String, Object> headers = new HashMap<>();
		headers.put("JMSDeliveryMode", DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i < expectedCount; i++) {
			template.sendBodyAndHeaders(expectedBody, headers);
		}

		int receivedEvents = jmsProcessor.getReceivedMessages();
		if (receivedEvents < expectedCount) {
			synchronized (jmsProcessor.getLock()) {
				jmsProcessor.getLock().wait(60000);
			}
		}
		assertEquals(expectedCount.intValue(), jmsProcessor.getReceivedMessages());
	}

	private Exchange createExchange() {
		Exchange exchange = new DefaultExchange(context);
		exchange.setUnitOfWork(UnitOfWorkHelper.createUoW(exchange));
		Message message = new DefaultMessage();
		message.setBody(expectedBody);
		exchange.setIn(message);
		return exchange;
	}

}
