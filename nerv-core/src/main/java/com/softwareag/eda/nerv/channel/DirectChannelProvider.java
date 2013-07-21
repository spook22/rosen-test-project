package com.softwareag.eda.nerv.channel;

public class DirectChannelProvider implements ChannelProvider {

	@Override
	public String channel(String type) {
		return "direct:" + type;
	}

}
