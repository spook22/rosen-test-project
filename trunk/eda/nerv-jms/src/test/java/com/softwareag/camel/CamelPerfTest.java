package com.softwareag.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.UnitOfWorkHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CamelPerfTest extends CamelTestSupport {

	private static final String directStart = "direct:start";

	private static final String expectedBody = "testBody";

	private static int expectedCount = 1000;

	@EndpointInject(uri = "vm:testPerformance")
	protected Endpoint resultEndpoint;

	protected DefaultProcessor processor = new DefaultProcessor(expectedCount);

	protected Producer producer;

	protected Exchange exchange;

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			@Override
			public void configure() {
				from(directStart).process(processor).to(resultEndpoint);
			}
		};
	}

	@Before
	public void before() throws Exception {
		producer = context.getEndpoint(directStart).createProducer();
		exchange = createExchange();
	}

	@Test
	public void testDirectComponent() throws Exception {
		for (int i = 0; i < expectedCount; i++) {
			producer.process(exchange);
		}
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
