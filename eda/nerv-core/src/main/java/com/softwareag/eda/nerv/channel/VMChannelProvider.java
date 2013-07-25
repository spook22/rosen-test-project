package com.softwareag.eda.nerv.channel;

public class VMChannelProvider implements ChannelProvider {

	@Override
	public String channel(String type) {
		return "vm:" + type + "?multipleConsumers=true&concurrentConsumers=10";
	}

}
