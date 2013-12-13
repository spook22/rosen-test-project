package com.softwareag.eda.nerv.channel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.softwareag.eda.nerv.event.Header;

public class EventTypeToChannelMapper implements Processor {

	private final String typeHeader = Header.TYPE.getName();

	private final String channelHeader = Header.CHANNEL.getName();

	private final ChannelProvider channelProvider = new VMChannelProvider();

	public String getTypeHeader() {
		return typeHeader;
	}

	public String getChannelHeader() {
		return channelHeader;
	}

	public ChannelProvider getChannelProvider() {
		return channelProvider;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		String type = in.getHeader(typeHeader, String.class);
		in.setHeader(channelHeader, channelProvider.channel(type));
	}

}
