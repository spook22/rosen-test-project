package com.softwareag.camel.um;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventListener;

public class UmEventListener implements nEventListener {

	private final UmEndpoint endpoint;

	private final Processor processor;

	public UmEventListener(UmEndpoint endpoint, Processor processor) {
		this.endpoint = endpoint;
		this.processor = processor;
	}

	@Override
	public void go(nConsumeEvent event) {
		try {
			Exchange exchange = endpoint.createExchange();
			Object body = new String(event.getEventData());
			exchange.getIn().setBody(body);
			processor.process(exchange);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
