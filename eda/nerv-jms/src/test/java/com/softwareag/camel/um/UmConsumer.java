package com.softwareag.camel.um;

import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class UmConsumer extends DefaultConsumer {

	private final UmEventListener listener;

	public UmConsumer(UmEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		listener = new UmEventListener(endpoint, processor);
	}

	@Override
	public UmEndpoint getEndpoint() {
		return (UmEndpoint) super.getEndpoint();
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		getEndpoint().getChannel().removeSubscriber(listener);
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		getEndpoint().getChannel().addSubscriber(listener);
	}

}
