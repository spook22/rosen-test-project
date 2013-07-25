package com.softwareag.eda.nerv.subscribe.route;

import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.model.RouteDefinition;

import com.softwareag.eda.nerv.subscribe.route.AbstractRoute;

public class DefaultPublishRoute extends AbstractRoute {

	private final String to;

	private ErrorHandlerBuilder errorHandlerBuilder;

	public DefaultPublishRoute(String from, String to) {
		super(from);
		this.to = to;
	}

	@Override
	public void setErrorHandlerBuilder(ErrorHandlerBuilder errorHandlerBuilder) {
		this.errorHandlerBuilder = errorHandlerBuilder;
	}

	@Override
	public void configure() throws Exception {
		RouteDefinition definition = from(channel);
		if (errorHandlerBuilder != null) {
			definition = definition.errorHandler(errorHandlerBuilder);
		}
		definition.to(to);
	}

}
