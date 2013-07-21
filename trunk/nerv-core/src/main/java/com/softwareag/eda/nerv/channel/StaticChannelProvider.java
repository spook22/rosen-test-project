package com.softwareag.eda.nerv.channel;

public class StaticChannelProvider implements ChannelProvider {

	private final String channel;

	public StaticChannelProvider(String channel) {
		this.channel = channel;
	}

	@Override
	public String channel(String type) {
		return channel; // The static channel provider does not care about the event type.
	}

}
