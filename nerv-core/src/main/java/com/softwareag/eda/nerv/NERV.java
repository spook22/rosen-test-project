package com.softwareag.eda.nerv;

import java.util.Map;

import org.apache.log4j.Logger;

import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.connection.NERVConnectionFactory;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class NERV implements Publisher, SubscriptionHandler {

	private static final Logger logger = Logger.getLogger(NERV.class);

	private static NERV instance;

	public static NERV instance() {
		if (instance == null) {
			synchronized (NERV.class) {
				if (instance == null) {
					instance = new NERV();
				}
			}
		}
		return instance;
	}

	protected static synchronized void destroy() {
		if (instance != null) {
			instance.connection.close();
		}
		instance = null;
	}

	private final NERVConnection connection;

	private NERV() throws NERVException {
		connection = NERVConnectionFactory.getDefaultConnection();
		logger.info("NERV was successfully initialized.");
	}

	@Override
	public void publish(String type, Object body) {
		connection.publish(type, body);
	}

	@Override
	public void publish(Map<String, Object> headers, Object body) {
		connection.publish(headers, body);
	}

	@Override
	public void publish(Event event) {
		connection.publish(event);
	}

	@Override
	public void subscribe(Subscription subscription) throws NERVException {
		connection.subscribe(subscription);
	}

	@Override
	public void unsubscribe(Subscription subscription) throws NERVException {
		connection.unsubscribe(subscription);
	}

}
