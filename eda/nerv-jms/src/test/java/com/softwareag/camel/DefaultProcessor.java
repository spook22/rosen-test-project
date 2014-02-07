package com.softwareag.camel;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DefaultProcessor implements Processor {

	protected final Object lock = new Object();

	protected final int expectedMessages;

	protected final AtomicInteger receivedMessages = new AtomicInteger();

	public Object getLock() {
		return lock;
	}

	public DefaultProcessor() {
		this(0);
	}

	public DefaultProcessor(int expectedMessages) {
		this.expectedMessages = expectedMessages;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		receivedMessages.incrementAndGet();
	}

}