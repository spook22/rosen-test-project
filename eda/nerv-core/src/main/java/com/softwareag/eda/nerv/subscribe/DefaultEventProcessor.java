package com.softwareag.eda.nerv.subscribe;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.event.Event;

public class DefaultEventProcessor implements Processor {

	private final Consumer consumer;

	public DefaultEventProcessor(Consumer consumer) {
		this.consumer = consumer;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Event event = new Event(exchange.getIn().getHeaders(), exchange.getIn().getBody());
		consumer.consume(event);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DefaultEventProcessor) {
			DefaultEventProcessor other = (DefaultEventProcessor) obj;
			return consumer.equals(other.consumer);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return consumer.hashCode();
	}

}
