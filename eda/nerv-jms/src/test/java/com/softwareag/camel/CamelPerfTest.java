package com.softwareag.camel;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;

import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelTestSupport;
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

	@Resource(name = "vmSjmsEndpoint")
	protected Endpoint vmSjmsEndpoint;

	@Resource(name = "sjmsEndpoint")
	protected Endpoint sjmsEndpoint;

	@Test
	public void testVmToSjmsComponent() throws Exception {
		log.info("Expected count set to " + expectedCount);
		template.setDefaultEndpoint(vmSjmsEndpoint);

		Map<String, Object> headers = new HashMap<>();
		headers.put("JMSDeliveryMode", DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i < expectedCount; i++) {
			template.sendBodyAndHeaders(expectedBody, headers);
		}

		int receivedEvents = jmsProcessor.getReceivedMessages();
		if (receivedEvents < expectedCount) {
			synchronized (jmsProcessor.getLock()) {
				jmsProcessor.getLock().wait(20000);
			}
		}
		assertEquals(expectedCount.intValue(), jmsProcessor.getReceivedMessages());
	}

}
