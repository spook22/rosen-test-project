package com.softwareag.eda.nerv.subscribe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Processor;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
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
		assertNotNull(route.getRouteCollection());
		assertNotNull(route.getRouteCollection().getRoutes());
		assertFalse(route.getRouteCollection().getRoutes().isEmpty());

		String id = "testId";
		route.getRouteCollection().getRoutes().get(0).setId(id);
		assertEquals(id, route.getId());
	}

	@Test
	public void testGetProcessor() {
		assertEquals(processor, route.getProcessor());
	}

	@Test
	public void testGetChannel() {
		assertEquals(channel, route.getChannel());
	}

	@Test(expected = RuntimeException.class)
	public void testGetIdNoRoutesDefinition() {
		route.setRouteCollection(null);
		route.getId();
	}

	@Test(expected = RuntimeException.class)
	public void testGetIdNoRoutes() {
		RoutesDefinition routesDefinition = new RoutesDefinition();
		routesDefinition.setRoutes(null);
		assertNull(routesDefinition.getRoutes());
		route.setRouteCollection(routesDefinition);
		route.getId();
	}

	@Test(expected = RuntimeException.class)
	public void testGetIdEmptyRoutes() {
		RoutesDefinition routesDefinition = new RoutesDefinition();
		route.setRouteCollection(routesDefinition);
		route.getId();
	}

	@Test
	public void testGetId() {
		String id = "testGetId";
		RoutesDefinition routesDefinition = new RoutesDefinition();
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setId(id);
		routesDefinition.getRoutes().add(routeDefinition);
		route.setRouteCollection(routesDefinition);
		assertEquals(id, route.getId());
	}

}
