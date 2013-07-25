package com.softwareag.eda.nerv.consumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.softwareag.eda.nerv.event.Event;

public abstract class AbstractConsumer implements TestConsumer {

	public static final Set<TestConsumer> consumers = Collections.synchronizedSet(new HashSet<TestConsumer>());

	protected final Object lock = new Object();

	protected final List<Event> events = Collections.synchronizedList(new ArrayList<Event>());

	protected final AtomicInteger receivedMessages = new AtomicInteger();

	public AbstractConsumer() {
		consumers.add(this);
	}

	@Override
	public List<Event> getEvents() {
		return Collections.unmodifiableList(events);
	}

	@Override
	public Object getLock() {
		return lock;
	}

}
