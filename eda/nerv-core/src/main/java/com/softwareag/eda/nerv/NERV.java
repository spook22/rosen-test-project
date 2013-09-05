package com.softwareag.eda.nerv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.channel.DirectChannelProvider;
import com.softwareag.eda.nerv.channel.StaticChannelProvider;
import com.softwareag.eda.nerv.channel.VMChannelProvider;
import com.softwareag.eda.nerv.component.SpringComponentResolver;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.connection.VMConnection;

public class NERV {

	private static final Logger logger = LoggerFactory.getLogger(NERV.class);

	public static final String PROP_CHANNEL_TYPE = "nerv.channel.type";

	public static final String PROP_CHANNEL_TYPE_DIRECT = "direct";

	public static final String PROP_CHANNEL_TYPE_VM = "vm";

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
			instance.destroyDefaultConnection();
			instance = null;
		}
	}

	public static final String PROP_CREATE_DEFAULT_CONNECTION = "create.default.connection";

	private NERVConnection defaultConnection;

	private final ContextProvider contextProvider;

	private NERV() throws NERVException {
		contextProvider = new SimpleContextProvider();
		defaultConnection = getDefaultConnection();
		logger.info("NERV was successfully initialized.");
	}

	public final NERVConnection getDefaultConnection() throws NERVException {
		if (defaultConnection == null) {
			if (Boolean.TRUE.toString().equals(
					System.getProperty(PROP_CREATE_DEFAULT_CONNECTION, Boolean.TRUE.toString()))) {
				createDefaultConnection();
			} else {
				throw new NERVException(
						"Default connection has not been set. Make sure NERV has been properly initialized.");
			}
		}
		return defaultConnection;
	}

	private ChannelProvider getChannelProvider() {
		String channelType = System.getProperty(PROP_CHANNEL_TYPE, PROP_CHANNEL_TYPE_VM);
		return channelType.equals(PROP_CHANNEL_TYPE_DIRECT) ? new DirectChannelProvider() : new VMChannelProvider();
	}

	private synchronized void createDefaultConnection() {
		if (defaultConnection == null) {
			setDefaultConnection(new VMConnection(contextProvider, getChannelProvider(), new SpringComponentResolver()));
		}
	}

	protected final synchronized void setDefaultConnection(NERVConnection connection) throws NERVException {
		if (defaultConnection != null) {
			throw new NERVException("Default connection has already been set.");
		}
		defaultConnection = connection;
	}

	protected final synchronized void destroyDefaultConnection() {
		if (defaultConnection != null) {
			defaultConnection.close();
			defaultConnection = null;
		}
	}

	public final NERVConnection createChannelConnection(String channel) {
		return new VMConnection(contextProvider, new StaticChannelProvider(channel), new SpringComponentResolver());
	}

	protected ContextProvider getContextProvider() {
		return contextProvider;
	}

}
