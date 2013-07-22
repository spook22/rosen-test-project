package com.softwareag.eda.nerv.subscribe.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RoutesDefinition;

public abstract class AbstractRoute extends RouteBuilder {

	protected final String channel;

	public AbstractRoute(String channel) {
		this.channel = channel;
	}

	public String getChannel() {
		return channel;
	}

	public String getId() throws RuntimeException {
		RoutesDefinition routesDefinition = getRouteCollection();
		if (routesDefinition != null && routesDefinition.getRoutes() != null && !routesDefinition.getRoutes().isEmpty()) {
			return routesDefinition.getRoutes().get(0).getId();
		} else {
			throw new RuntimeException("Cannot obtain route ID. Make sure the route has been initialized.");
		}
	}

}
