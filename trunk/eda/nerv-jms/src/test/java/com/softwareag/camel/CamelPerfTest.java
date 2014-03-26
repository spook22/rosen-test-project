package com.softwareag.camel;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;

import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.CaseInsensitiveMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softwareag.Measurement;
import com.softwareag.Utils;

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

	@Before
	public void before() throws Exception {
		log.info("Expected count set to " + expectedCount);
		template.setDefaultEndpoint(sjmsEndpoint);

		// for (int i = 0; i < 1000; i++) {
		// template.sendBody(expectedBody);
		// }
		// Thread.sleep(5000);

		jmsProcessor.clean();
		assertEquals(0, jmsProcessor.getReceivedMessages());
	}

	@Test
	public void testVmToSjmsNoPersistence() throws Exception {
		Map<String, Object> headers = new CaseInsensitiveMap();
		headers.put("JMSDeliveryMode", DeliveryMode.NON_PERSISTENT);
		long start = System.currentTimeMillis();
		send(headers);
		validate();
		record("testVmToSjmsNoPersistence", System.currentTimeMillis() - start);
	}

	@Test
	public void testVmToSjmsPersistence() throws Exception {
		long start = System.currentTimeMillis();
		send(null);
		validate();
		record("testVmToSjmsPersistence", System.currentTimeMillis() - start);
	}

	private void send(Map<String, Object> headers) throws Exception {
		if (headers == null) {
			headers = new HashMap<>();
		}
		for (int i = 0; i < expectedCount; i++) {
			template.sendBodyAndHeaders(expectedBody, headers);
		}
	}

	private void validate() throws Exception {
		int receivedEvents = jmsProcessor.getReceivedMessages();
		if (receivedEvents < expectedCount) {
			synchronized (jmsProcessor.getLock()) {
				jmsProcessor.getLock().wait(60000);
			}
		}
		assertEquals(expectedCount.intValue(), jmsProcessor.getReceivedMessages());
	}

	private void record(String testName, long milliseconds) {
		Measurement measurement = new Measurement(System.currentTimeMillis(), testName, expectedCount, milliseconds);
		Utils.record(measurement);
	}

}
