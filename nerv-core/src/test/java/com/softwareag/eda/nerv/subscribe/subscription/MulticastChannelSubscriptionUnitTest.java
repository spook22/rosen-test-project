package com.softwareag.eda.nerv.subscribe.subscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.camel.Processor;
import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.softwareag.eda.nerv.consumer.BasicConsumer;
import com.softwareag.eda.nerv.subscribe.DefaultEventProcessor;
import com.softwareag.eda.nerv.subscribe.subscription.MulticastRoute;

public class MulticastChannelSubscriptionUnitTest {

	private final String channel = "ChannelSubscriptionUnitTest";

	private final Processor processor = new DefaultEventProcessor(new BasicConsumer());

	private MulticastRoute subscription;

	@Before
	public void before() {
		subscription = new MulticastRoute(channel, processor);
		assertEquals(channel, subscription.getChannel());
		assertFalse(subscription.isEmpty());

	}

	@After
	public void after() {
		subscription.setRouteCollection(new RoutesDefinition());
		subscription.removeProcessor(processor);
		assertTrue(subscription.isEmpty());
	}

	@Test
	public void testCheckInitialized() throws Exception {
		subscription.checkInitialized();
		subscription.checkInitialized(); // This should do nothing.
	}

	@Test
	public void testCheckInitializedWithErrorHandler() throws Exception {
		subscription = new MulticastRoute(channel, processor);
		subscription.getContext().setErrorHandlerBuilder(new NoErrorHandlerBuilder());
		subscription.checkInitialized();
	}

	@Test(expected = RuntimeException.class)
	public void testGetIdNoRoutesDefinition() {
		subscription.setRouteCollection(null);
		subscription.getId();
	}

	@Test(expected = RuntimeException.class)
	public void testGetIdNoRoutes() {
		RoutesDefinition routesDefinition = new RoutesDefinition();
		routesDefinition.setRoutes(null);
		assertNull(routesDefinition.getRoutes());
		subscription.setRouteCollection(routesDefinition);
		subscription.getId();
	}

	@Test(expected = RuntimeException.class)
	public void testGetIdEmptyRoutes() {
		RoutesDefinition routesDefinition = new RoutesDefinition();
		subscription.setRouteCollection(routesDefinition);
		subscription.getId();
	}

	@Test
	public void testGetId() {
		String id = "testGetId";
		RoutesDefinition routesDefinition = new RoutesDefinition();
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setId(id);
		routesDefinition.getRoutes().add(routeDefinition);
		subscription.setRouteCollection(routesDefinition);
		assertEquals(id, subscription.getId());
	}

}
