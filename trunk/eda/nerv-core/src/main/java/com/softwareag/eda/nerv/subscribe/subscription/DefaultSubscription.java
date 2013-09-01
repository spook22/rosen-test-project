package com.softwareag.eda.nerv.subscribe.subscription;

import com.softwareag.eda.nerv.consume.Consumer;

public class DefaultSubscription implements Subscription {

	private final String type;

	private final Consumer consumer;

	public DefaultSubscription(String channel, Consumer consumer) {
		this.type = channel;
		this.consumer = consumer;
	}

	@Override
	public String type() {
		return type;
	}

	@Override
	public Consumer consumer() {
		return consumer;
	}

}
