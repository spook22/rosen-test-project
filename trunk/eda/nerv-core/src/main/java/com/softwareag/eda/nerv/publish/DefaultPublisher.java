package com.softwareag.eda.nerv.publish;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.camel.ResolveEndpointFailedException;
import org.apache.camel.impl.DefaultMessage;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.channel.VMChannelProvider;
import com.softwareag.eda.nerv.component.ComponentResolver;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.EventDecorator;
import com.softwareag.eda.nerv.publish.EventPublishListener.PublishOperation;

public class DefaultPublisher implements Publisher {

	private final ContextProvider contextProvider;

	private final ChannelProvider channelProvider;

	private final ComponentResolver componentResolver;

	private EventDecorator decorator;

	private final ConcurrentHashMap<String, Producer> producersCache = new ConcurrentHashMap<String, Producer>();

	private final Set<EventPublishListener> eventPublishListeners = new HashSet<EventPublishListener>();

	public DefaultPublisher(ContextProvider contextProvider, ChannelProvider channelProvider,
			ComponentResolver componentResolver) {
		this(contextProvider, channelProvider, componentResolver, null);
	}

	public DefaultPublisher(ContextProvider contextProvider, ChannelProvider channelProvider,
			ComponentResolver componentResolver, EventDecorator decorator) {
		this.contextProvider = contextProvider;
		this.channelProvider = channelProvider;
		this.componentResolver = componentResolver;
		this.decorator = decorator;
	}

	public void setDecorator(EventDecorator decorator) {
		this.decorator = decorator;
	}

	private CamelContext context() {
		return contextProvider.context();
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
		notifyListeners(PublishOperation.PRE_PUBLISH, channel, event);
		send(channel, event);
		notifyListeners(PublishOperation.POST_PUBLISH, channel, event);
	}

	public void registerEventPublishListner(EventPublishListener listener) {
		eventPublishListeners.add(listener);
	}

	public void unregisterEventPublishListner(EventPublishListener listener) {
		eventPublishListeners.add(listener);
	}

	private ChannelProvider internalProvider = new VMChannelProvider();

	private void notifyListeners(PublishOperation operation, String channel, Event event) {
		Event internalEvent = new Event("EventPublished", event);
		send(internalProvider.channel(internalEvent.getType()), internalEvent);
		for (EventPublishListener eventPublishListener : eventPublishListeners) {
			eventPublishListener.onPublish(operation, channel, event);
		}
	}

	private Producer getProducer(String uri) throws Exception {
		Producer producer = producersCache.get(uri);
		if (producer == null) {
			Endpoint endpoint;
			try {
				endpoint = context().getEndpoint(uri);
			} catch (ResolveEndpointFailedException e) {
				endpoint = resolveEndpoint(uri);
			}
			producer = endpoint.createProducer();
			Producer previous = producersCache.putIfAbsent(uri, producer);
			if (previous != null) {
				// Someone already created it so use the previous instance.
				producer = previous;
			}
		}
		return producer;
	}

	private Endpoint resolveEndpoint(String uri) throws Exception {
		String componentName = extractComponentName(uri);
		Endpoint endpoint = getComponent(componentName).createEndpoint(uri);
		context().addEndpoint(uri, endpoint);
		return endpoint;
	}

	private Component getComponent(String componentName) {
		return componentResolver.resolve(componentName);
	}

	private String extractComponentName(String uri) {
		int index = uri.indexOf(':');
		if (index != -1) {
			return uri.substring(0, index);
		} else {
			String msg = String.format(
					"Endpoint %s is invalid. It should have the following syntax: <component>:[type:]<endpoint>.", uri);
			throw new NERVException(msg);
		}
	}

	private void send(String uri, Event event) {
		try {
			Producer producer = getProducer(uri);
			Exchange exchange = producer.createExchange();
			Message message = new DefaultMessage();
			message.setBody(event.getBody());
			message.setHeaders(event.getHeaders());
			exchange.setIn(message);
			producer.process(exchange);
		} catch (Exception e) {
			throw new NERVException("Cannot send event to channel: " + uri, e);
		}
	}
}
