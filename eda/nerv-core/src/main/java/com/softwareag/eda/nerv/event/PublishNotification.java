package com.softwareag.eda.nerv.event;

import com.softwareag.eda.nerv.publish.EventPublishListener.PublishOperation;

public class PublishNotification {

	public static final String TYPE = "PublishNotification";

	private PublishOperation operation;

	private String channel;

	private Event event;

	public PublishNotification(PublishOperation operation, String channel, Event event) {
		this.operation = operation;
		this.channel = channel;
		this.event = event;
	}

	public PublishOperation getOperation() {
		return operation;
	}

	public String getChannel() {
		return channel;
	}

	public Event getEvent() {
		return event;
	}

}
