package com.softwareag.eda.nerv;

import com.softwareag.eda.nerv.channel.StaticChannelProvider;
import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.connection.VMConnection;

public class NERVConnectionFactory {

	public static final String PROP_CREATE_DEFAULT_CONNECTION = "create.default.connection";

	private static NERVConnection defaultConnection;

	public static final NERVConnection getDefaultConnection() throws NERVException {
		if (defaultConnection == null) {
			if (Boolean.TRUE.toString().equals(System.getProperty(PROP_CREATE_DEFAULT_CONNECTION, Boolean.TRUE.toString()))) {
				createDefaultConnection();
			} else {
				throw new NERVException("Default connection has not been set. Make sure NERV has been properly initialized.");
			}
		}
		return defaultConnection;
	}

	private static final synchronized void createDefaultConnection() {
		if (defaultConnection == null) {
			setDefaultConnection(new VMConnection());
		}
	}

	protected static final synchronized void setDefaultConnection(NERVConnection connection) throws NERVException {
		if (defaultConnection != null) {
			throw new NERVException("Default connection has already been set.");
		}
		defaultConnection = connection;
	}

	protected static final synchronized void destroyDefaultConnection() {
		if (defaultConnection != null) {
			defaultConnection.close();
			defaultConnection = null;
		}
	}

	public static NERVConnection createChannelConnection(String channel) {
		return new VMConnection(new StaticChannelProvider(channel));
	}

}
