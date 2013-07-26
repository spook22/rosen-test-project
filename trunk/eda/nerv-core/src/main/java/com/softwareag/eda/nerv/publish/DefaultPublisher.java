package com.softwareag.eda.nerv.publish;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	private final Set<EventPublishListener> eventPublishListeners = new HashSet<EventPublishListener>();

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
		notifyListeners(event, true);
		String channel = channelProvider.channel(event.getType());
		producer().sendBodyAndHeaders(channel, event.getBody(), event.getHeaders());
		notifyListeners(event, false);
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

	public void registerEventPublishListner(EventPublishListener listener) {
		eventPublishListeners.add(listener);
	}

	public void unregisterEventPublishListner(EventPublishListener listener) {
		eventPublishListeners.add(listener);
	}

	private void notifyListeners(Event event, boolean prePublish) {
		for (EventPublishListener eventPublishListener : eventPublishListeners) {
			if (prePublish) {
				eventPublishListener.prePublish(event);
			} else {
				eventPublishListener.postPublish(event);
			}
		}
	}

}
