package com.softwareag.eda.nerv;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;

import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.channel.DirectChannelProvider;
import com.softwareag.eda.nerv.channel.VMChannelProvider;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.event.EventDecorator;
import com.softwareag.eda.nerv.event.EventIdDecorator;
import com.softwareag.eda.nerv.event.StartHeaderDecorator;
import com.softwareag.eda.nerv.publish.DefaultPublisher;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.DefaultSubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class NERV implements Publisher, SubscriptionHandler, ContextProvider {

	private static final Logger logger = Logger.getLogger(NERV.class);

	public static final String PROP_CHANNEL_TYPE = "nerv.channel.type";

	public static final String PROP_CHANNEL_TYPE_DIRECT = "direct";

	public static final String PROP_CHANNEL_TYPE_VM = "vm";

	private static NERV instance;

	public static NERV instance() {
		if (instance == null) {
			try {
				setInstance(new NERV());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	protected static synchronized void setInstance(NERV nerv) {
		instance = nerv;
	}

	private final CamelContext context;

	private final ChannelProvider channelProvider;

	private final Publisher publisher;

	private final SubscriptionHandler subscriptionHandler;

	private final String channelType;

	protected NERV() throws Exception {
		context = createContext();
		context.start();
		channelType = System.getProperty(PROP_CHANNEL_TYPE, PROP_CHANNEL_TYPE_VM);
		channelProvider = channelType.equals(PROP_CHANNEL_TYPE_DIRECT) ? new DirectChannelProvider() : new VMChannelProvider();
		publisher = new DefaultPublisher(this, channelProvider, createDecorator());
		subscriptionHandler = new DefaultSubscriptionHandler(this, channelProvider);
		logger.info("Initialized NERV with channel type: " + channelType);
	}

	private EventDecorator createDecorator() {
		return new EventIdDecorator(new StartHeaderDecorator());
	}

	private CamelContext createContext() {
		return new DefaultCamelContext();
	}

	@Override
	public CamelContext context() {
		return context;
	}

	@Override
	public void publish(String type, Object body) {
		publisher.publish(new Event(type, body));
	}

	@Override
	public void publish(Map<String, Object> headers, Object body) {
		publisher.publish(new Event(headers, body));
	}

	@Override
	public void publish(Event event) {
		publisher.publish(event);
	}

	@Override
	public void subscribe(Subscription subscription) throws Exception {
		subscriptionHandler.subscribe(subscription);
	}

	@Override
	public void unsubscribe(Subscription subscription) throws Exception {
		subscriptionHandler.unsubscribe(subscription);
	}

}
