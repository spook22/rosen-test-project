package com.softwareag.camel;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultProcessor implements Processor {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultProcessor.class);

	protected final Object lock = new Object();

	protected int expectedMessages;

	protected final AtomicInteger receivedMessages = new AtomicInteger();

	public int getReceivedMessages() {
		return receivedMessages.get();
	}

	public Object getLock() {
		return lock;
	}	

	public int getExpectedMessages() {
		return expectedMessages;
	}

	public void setExpectedMessages(int expectedMessages) {
		this.expectedMessages = expectedMessages;
	}

	public DefaultProcessor() {
		this(0);
	}

	public DefaultProcessor(int expectedMessages) {
		this.expectedMessages = expectedMessages;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		synchronized (lock) {
			if (receivedMessages.incrementAndGet() >= expectedMessages) {
				lock.notify();
			}
		}
		logger.info(exchange.getIn().getBody(String.class));
	}

	public void clean() {
		receivedMessages.set(0);
	}

}