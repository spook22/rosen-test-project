package com.softwareag.eda.nerv;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.DynamicRouterDefinition;
import org.apache.camel.model.RouteDefinition;

import com.softwareag.eda.nerv.channel.EventTypeToChannelMapper;
import com.softwareag.eda.nerv.event.EventHeadersDecorator;
import com.softwareag.eda.nerv.event.EventIdHeaderDecorator;
import com.softwareag.eda.nerv.event.StartHeaderDecorator;
import com.softwareag.eda.nerv.intercept.EventsInterceptor;
import com.softwareag.eda.nerv.route.NERVDynamicRouter;

public class NERVDefaultRouteBuilder extends RouteBuilder {

	private final String fromEndpoint;

	public NERVDefaultRouteBuilder(String fromEndpoint) {
		this.fromEndpoint = fromEndpoint;
	}

	@Override
	public void configure() throws Exception {
		RouteDefinition definition = from(fromEndpoint).routeId("nervDefaultRoute");
		EventHeadersDecorator eventHeadersProcessor = new EventHeadersDecorator(new StartHeaderDecorator(
				new EventIdHeaderDecorator()));
		definition = definition.process(eventHeadersProcessor);
		definition.setId("nervHeadersDecorator");

		definition = definition.process(new EventTypeToChannelMapper());
		definition.setId("nervChannelMapper");

		definition = definition.process(new EventsInterceptor());
		definition.setId("nervEventsInterceptor");

		DynamicRouterDefinition<RouteDefinition> dynamicRouteDefinition = definition.dynamicRouter().method(
				new NERVDynamicRouter());
		dynamicRouteDefinition.setId("nervDynamicRouter");
		definition = dynamicRouteDefinition.end();
	}

}
