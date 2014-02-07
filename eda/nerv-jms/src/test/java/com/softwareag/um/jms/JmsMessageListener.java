package com.softwareag.um.jms;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Message;
import javax.jms.MessageListener;

public class JmsMessageListener implements MessageListener {

	private final int expectedEvents;

	private final AtomicInteger receivedEvents = new AtomicInteger();

	private final Object lock = new Object();

	public JmsMessageListener(int expectedEvents) {
		super();
		this.expectedEvents = expectedEvents;
	}

	public int getReceivedEvents() {
		return receivedEvents.get();
	}

	public Object getLock() {
		return lock;
	}

	@Override
	public void onMessage(Message event) {
		synchronized (lock) {
			if (receivedEvents.incrementAndGet() >= expectedEvents) {
				lock.notify();
			}
		}
	}

}
