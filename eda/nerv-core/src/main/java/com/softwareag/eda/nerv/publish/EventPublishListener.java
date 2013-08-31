package com.softwareag.eda.nerv.publish;

import com.softwareag.eda.nerv.event.Event;

public interface EventPublishListener {

	public static enum PublishOperation {
		PRE_PUBLISH, POST_PUBLISH
	}

	void onPublish(PublishOperation operation, String channel, Event event);

}
