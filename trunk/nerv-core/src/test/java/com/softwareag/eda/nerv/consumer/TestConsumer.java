package com.softwareag.eda.nerv.consumer;

import java.util.List;

import com.softwareag.eda.nerv.consume.Consumer;
import com.softwareag.eda.nerv.event.Event;

public interface TestConsumer extends Consumer {

	List<Event> getEvents();

	Object getLock();

}
