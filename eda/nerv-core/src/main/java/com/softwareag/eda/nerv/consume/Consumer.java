package com.softwareag.eda.nerv.consume;

import com.softwareag.eda.nerv.event.Event;

public interface Consumer {

	void consume(Event event);

}
