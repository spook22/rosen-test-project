package com.softwareag.eda.nerv.subscribe.subscription;

import org.apache.camel.Processor;

public class DefaultRoute extends AbstractChannelRoute {

	private final Processor processor;

	public DefaultRoute(String channel, Processor processor) {
		super(channel);
		this.processor = processor;
	}

	public Processor getProcessor() {
		return processor;
	}

	@Override
	public void configure() throws Exception {
		from(channel).process(processor);
	}
}
