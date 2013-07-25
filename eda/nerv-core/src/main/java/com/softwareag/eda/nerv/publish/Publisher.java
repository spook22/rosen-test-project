package com.softwareag.eda.nerv.publish;

import java.util.Map;

import com.softwareag.eda.nerv.event.Event;

public interface Publisher {

	void publish(String type, Object body);

	void publish(Map<String, Object> headers, Object body);

	void publish(Event event);

}
