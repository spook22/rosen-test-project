package com.softwareag.camel;

import javax.annotation.Resource;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CamelTest extends CamelTestSupport {

	@Resource
	private DefaultProcessor jmsProcessor;

	@Resource
	protected ProducerTemplate template;
	
	@Test
	public void test() throws Exception {
		int expected = 10;
		jmsProcessor.setExpectedMessages(expected);
		
		for (int i = 0; i < expected; i++) {
//			template.sendBody("direct:start", "Test Message");
			template.asyncSendBody("direct:start", "Test Message");
//			template.asyncCallbackSendBody("direct:start", "Test Message", myCallback);
		}
		
		if (jmsProcessor.getReceivedMessages() < expected) {
			synchronized (jmsProcessor.getLock()) {
				jmsProcessor.getLock().wait(5000);
			}
		}
		assertEquals(expected, jmsProcessor.getReceivedMessages());
	}

}
