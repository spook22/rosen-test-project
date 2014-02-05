package com.softwareag.eda.nerv.event;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class EventHeadersDecorator implements Processor {

	private final EventDecorator decorator;

	public EventHeadersDecorator(EventDecorator decorator) {
		super();
		this.decorator = decorator;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		Event event = new Event(in.getHeaders(), in.getBody());
		decorator.decorate(event);
	}

}
