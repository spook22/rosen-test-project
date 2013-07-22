package com.softwareag.eda.nerv.subscribe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.camel.Processor;
import org.junit.Test;

import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.subscribe.route.DefaultRoute;

public class DefaultRouteUnitTest {

	private final String channel = "DefaultRouteUnitTest";

	private final Processor processor = new DefaultEventProcessor(new BasicConsumer());

	private final DefaultRoute route = new DefaultRoute(channel, processor);

	@Test
	public void testConfigure() throws Exception {
		route.configure();
		assertNotNull(route.getId());
	}

	@Test
	public void testGetProcessor() {
		assertEquals(processor, route.getProcessor());
	}

	@Test
	public void testGetChannel() {
		assertEquals(channel, route.getChannel());
	}

}
