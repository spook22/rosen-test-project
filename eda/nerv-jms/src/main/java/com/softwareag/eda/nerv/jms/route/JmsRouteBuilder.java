package com.softwareag.eda.nerv.jms.route;

import org.apache.camel.builder.RouteBuilder;

public class JmsRouteBuilder extends RouteBuilder {

	private final String fromEndpoint;
	
	private final String toEndpoint;

	public JmsRouteBuilder(String fromEndpoint, String toEndpoint) {
		this.fromEndpoint = fromEndpoint;
		this.toEndpoint = toEndpoint;
	}

	public String getFromEndpoint() {
		return fromEndpoint;
	}

	public String getToEndpoint() {
		return toEndpoint;
	}

	@Override
	public void configure() throws Exception {
		from(fromEndpoint).routeId(fromEndpoint + "->" + toEndpoint).to(toEndpoint);
	}

}
