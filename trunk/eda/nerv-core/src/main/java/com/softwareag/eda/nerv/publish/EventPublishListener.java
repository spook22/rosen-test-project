package com.softwareag.eda.nerv.publish;

import com.softwareag.eda.nerv.event.Event;

public interface EventPublishListener {

	void prePublish(Event event);

	void postPublish(Event event);

}
