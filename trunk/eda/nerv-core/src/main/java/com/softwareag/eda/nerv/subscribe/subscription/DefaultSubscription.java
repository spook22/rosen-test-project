package com.softwareag.eda.nerv.subscribe.subscription;

import com.softwareag.eda.nerv.consume.Consumer;

public class DefaultSubscription implements Subscription {

	private final String channel;

	private final Consumer consumer;

	public DefaultSubscription(String channel, Consumer consumer) {
		this.channel = channel;
		this.consumer = consumer;
	}

	@Override
	public String channel() {
		return channel;
	}

	@Override
	public Consumer consumer() {
		return consumer;
	}

}
