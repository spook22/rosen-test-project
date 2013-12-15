package com.softwareag.eda.nerv.connection;

import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.component.ComponentResolver;
import com.softwareag.eda.nerv.context.ContextProvider;
import com.softwareag.eda.nerv.event.EventDecorator;
import com.softwareag.eda.nerv.event.EventIdHeaderDecorator;
import com.softwareag.eda.nerv.event.StartHeaderDecorator;
import com.softwareag.eda.nerv.publish.DefaultPublisher;
import com.softwareag.eda.nerv.subscribe.handler.DefaultSubscriptionHandler;

public class VMConnection extends AbstractConnection {

	public VMConnection(ContextProvider contextProvider, ChannelProvider channelProvider, ComponentResolver componentResolver) {
		super(contextProvider, new DefaultPublisher(contextProvider, channelProvider, componentResolver, createDecorator()),
				new DefaultSubscriptionHandler(contextProvider, channelProvider));
	}

	private static EventDecorator createDecorator() {
		return new EventIdHeaderDecorator(new StartHeaderDecorator());
	}

}
