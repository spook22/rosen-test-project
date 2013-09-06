package com.softwareag.eda.nerv.subscribe.route;

import org.apache.camel.Processor;

public class DefaultSubscriptionRoute extends AbstractRoute {

	private final Processor processor;

	public DefaultSubscriptionRoute(String channel, Processor processor) {
		super(channel);
		this.processor = processor;
	}

	public Processor getProcessor() {
		return processor;
	}

	@Override
	public void configure() throws Exception {
		from(channel).routeId(channel + "->" + processor).process(processor);
	}
}
