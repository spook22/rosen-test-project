package com.softwareag.eda.nerv.route;

import org.apache.camel.builder.RouteBuilder;

import com.softwareag.eda.nerv.channel.EventTypeToChannelMapper;
import com.softwareag.eda.nerv.event.EventHeadersDecorator;
import com.softwareag.eda.nerv.event.EventIdHeaderDecorator;
import com.softwareag.eda.nerv.event.StartHeaderDecorator;
import com.softwareag.eda.nerv.intercept.EventsInterceptor;

public class NERVDefaultRouteBuilder extends RouteBuilder {

	private final String fromEndpoint;

	public NERVDefaultRouteBuilder(String fromEndpoint) {
		this.fromEndpoint = fromEndpoint;
	}

	@Override
	public void configure() throws Exception {
		StartHeaderDecorator startHeaderDecorator = new StartHeaderDecorator(new EventIdHeaderDecorator());
		EventHeadersDecorator eventHeadersProcessor = new EventHeadersDecorator(startHeaderDecorator);
		from(fromEndpoint).routeId("nervDefaultRoute")
			.process(eventHeadersProcessor)
			.process(new EventTypeToChannelMapper())
			.process(EventsInterceptor.instance())
			.dynamicRouter()
				.method(new NERVDynamicRouter())
			.end();
	}

}
