package com.softwareag.eda.nerv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.channel.StaticChannelProvider;
import com.softwareag.eda.nerv.channel.VMChannelProvider;
import com.softwareag.eda.nerv.component.SpringComponentResolver;
import com.softwareag.eda.nerv.connection.DefaultConnection;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.connection.VMConnection;
import com.softwareag.eda.nerv.context.ContextProvider;
import com.softwareag.eda.nerv.context.SimpleContextProvider;
import com.softwareag.eda.nerv.publish.NERVPublisher;
import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.DefaultSubscriptionHandler;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;

public class NERV {

	private static final Logger logger = LoggerFactory.getLogger(NERV.class);

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
			instance.close();
			instance = null;
		}
	}

	protected NERVConnection defaultConnection;

	private final ContextProvider contextProvider;

	private NERV() throws NERVException {
		contextProvider = new SimpleContextProvider();
		defaultConnection = getDefaultConnection();
		logger.info("NERV was successfully initialized.");
	}

	public final NERVConnection getDefaultConnection() throws NERVException {
		if (defaultConnection == null) {
			createDefaultConnection();
		}
		return defaultConnection;
	}

	private synchronized void createDefaultConnection() {
		if (defaultConnection == null) {
			Publisher publisher = new NERVPublisher(getContextProvider(), new StaticChannelProvider("direct-vm:nerv"), new SpringComponentResolver());
			SubscriptionHandler subscriptionHandler = new DefaultSubscriptionHandler(getContextProvider(), new VMChannelProvider());
			setDefaultConnection(new DefaultConnection(getContextProvider(), publisher, subscriptionHandler));
		}
	}

	protected final synchronized void setDefaultConnection(NERVConnection connection) throws NERVException {
		if (defaultConnection != null) {
			throw new NERVException("Default connection has already been set.");
		}
		defaultConnection = connection;
	}

	protected final synchronized void close() {
		if (defaultConnection != null) {
			defaultConnection.close();
			defaultConnection = null;
		}
	}

	public final NERVConnection createChannelConnection(String channel) {
		return new VMConnection(getContextProvider(), new StaticChannelProvider(channel), new SpringComponentResolver());
	}

	public ContextProvider getContextProvider() {
		return contextProvider;
	}

}
