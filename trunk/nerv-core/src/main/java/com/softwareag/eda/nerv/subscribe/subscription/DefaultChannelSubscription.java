package com.softwareag.eda.nerv.subscribe.subscription;

import org.apache.camel.Processor;

public class DefaultChannelSubscription extends AbstractChannelSubscription {

	private final Processor processor;

	public DefaultChannelSubscription(String channel, Processor processor) {
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
