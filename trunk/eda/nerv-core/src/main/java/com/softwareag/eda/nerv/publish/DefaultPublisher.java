package com.softwareag.eda.nerv.publish;

import java.util.Map;

import org.apache.camel.ProducerTemplate;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.EventDecorator;

public class DefaultPublisher implements Publisher {

	private final ContextProvider contextProvider;

	private final ChannelProvider channelProvider;

	private EventDecorator decorator;

	private ProducerTemplate producer;

	public DefaultPublisher(ContextProvider contextProvider, ChannelProvider channelProvider) {
		this(contextProvider, channelProvider, null);
	}

	public DefaultPublisher(ContextProvider contextProvider, ChannelProvider channelProvider, EventDecorator decorator) {
		this.contextProvider = contextProvider;
		this.channelProvider = channelProvider;
		this.decorator = decorator;
	}

	public void setDecorator(EventDecorator decorator) {
		this.decorator = decorator;
	}

	@Override
	public void publish(String type, Object body) {
		publish(new Event(type, body));
	}

	@Override
	public void publish(Map<String, Object> headers, Object body) {
		publish(new Event(headers, body));

	}

	@Override
	public void publish(Event event) {
		if (decorator != null) {
			decorator.decorate(event);
		}
		String channel = channelProvider.channel(event.getType());
		producer().sendBodyAndHeaders(channel, event.getBody(), event.getHeaders());
	}

	private ProducerTemplate producer() {
		if (producer == null) {
			producer = createProducer();
		}
		return producer;
	}

	private ProducerTemplate createProducer() {
		return contextProvider.context().createProducerTemplate();
	}

}
