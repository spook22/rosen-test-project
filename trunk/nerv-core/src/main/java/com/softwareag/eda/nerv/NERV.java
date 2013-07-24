package com.softwareag.eda.nerv;

import org.apache.log4j.Logger;

import com.softwareag.eda.nerv.channel.StaticChannelProvider;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.connection.VMConnection;

public class NERV {

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
			instance.destroyDefaultConnection();
			instance = null;
		}
	}

	public static final String PROP_CREATE_DEFAULT_CONNECTION = "create.default.connection";

	private NERVConnection defaultConnection;

	private NERV() throws NERVException {
		defaultConnection = getDefaultConnection();
		logger.info("NERV was successfully initialized.");
	}

	public final NERVConnection getDefaultConnection() throws NERVException {
		if (defaultConnection == null) {
			if (Boolean.TRUE.toString().equals(System.getProperty(PROP_CREATE_DEFAULT_CONNECTION, Boolean.TRUE.toString()))) {
				createDefaultConnection();
			} else {
				throw new NERVException("Default connection has not been set. Make sure NERV has been properly initialized.");
			}
		}
		return defaultConnection;
	}

	private final synchronized void createDefaultConnection() {
		if (defaultConnection == null) {
			setDefaultConnection(new VMConnection());
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
		return new VMConnection(new StaticChannelProvider(channel));
	}

}
