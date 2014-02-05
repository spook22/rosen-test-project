package com.softwareag.camel;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.UnitOfWorkHelper;
import org.junit.Test;

public class PerfTest extends CamelTestSupport {

	private static final String directStart = "direct:start";

	private static final String expectedBody = "testBody";

	private static int expectedCount = 500000;

	@EndpointInject(uri = "vm:result")
	protected Endpoint resultEndpoint;

	@Produce(uri = directStart)
	protected ProducerTemplate template;

	protected DefaultProcessor processor = new DefaultProcessor(expectedCount);

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			@Override
			public void configure() {
				from(directStart).process(processor);
			}
		};
	}

	@Test
	public void testDirectComponent() throws Exception {
		Producer producer = context.getEndpoint(directStart).createProducer();
		Exchange exchange = createExchange();
		for (int i = 0; i < expectedCount; i++) {
			producer.process(exchange);
		}
	}

	@Test
	public void testProcessor() throws Exception {
		Exchange exchange = createExchange();
		for (int i = 0; i < expectedCount; i++) {
			processor.process(exchange);
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

	public static class DefaultProcessor implements Processor {

		protected final Object lock = new Object();

		protected final int expectedMessages;

		protected final AtomicInteger receivedMessages = new AtomicInteger();

		public Object getLock() {
			return lock;
		}

		public DefaultProcessor() {
			this(0);
		}

		public DefaultProcessor(int expectedMessages) {
			this.expectedMessages = expectedMessages;
		}

		@Override
		public void process(Exchange exchange) throws Exception {
			receivedMessages.incrementAndGet();
		}

	}

}
