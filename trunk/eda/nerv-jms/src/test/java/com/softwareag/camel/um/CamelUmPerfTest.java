package com.softwareag.camel.um;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CamelUmPerfTest extends CamelTestSupport {

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce
	protected ProducerTemplate template;

	@Test
	public void test() throws Exception {
		String expectedBody = "CamelUmPerfTest";
		resultEndpoint.expectedBodiesReceived(expectedBody);
		template.setDefaultEndpointUri("um:topic:CamelUmPerfTest");
		template.sendBody(expectedBody);
		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void testMultiple() throws Exception {
		String expectedBody = "CamelUmPerfTest";
		int messageCount = 10000;
		resultEndpoint.expectedMessageCount(messageCount);
		template.setDefaultEndpointUri("um:topic:CamelUmPerfTest");
		for (int i = 0; i < messageCount; i++) {
			template.sendBody(expectedBody);
		}
		resultEndpoint.assertIsSatisfied();
	}

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			@Override
			public void configure() {
				UmComponent umComponent = new UmComponent();
				umComponent.setRname("nsp://127.0.0.1:9000");
				context.addComponent("um", umComponent);

				from("um:topic:CamelUmPerfTest").to("mock:result");
			}
		};
	}

}
