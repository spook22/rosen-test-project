package com.softwareag.eda.nerv.consumer;

import com.softwareag.eda.nerv.event.Event;

public class BasicConsumer extends AbstractConsumer {

	private final int expectedMessages;

	public BasicConsumer() {
		this(0);
	}

	public BasicConsumer(int expectedMessages) {
		this.expectedMessages = expectedMessages;
	}

	@Override
	public void consume(Event event) {
		events.add(event);
		if (receivedMessages.incrementAndGet() >= expectedMessages) {
			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}

}
