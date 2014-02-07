package com.softwareag.um.nativeapi;

import java.util.concurrent.atomic.AtomicInteger;

import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventListener;

public class EventListener implements nEventListener {

	private final int expectedEvents;

	private final AtomicInteger receivedEvents = new AtomicInteger();

	private final Object lock = new Object();

	public EventListener(int expectedEvents) {
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
	public void go(nConsumeEvent event) {
		synchronized (lock) {
			if (receivedEvents.incrementAndGet() >= expectedEvents) {
				lock.notify();
			}
		}
	}

}
