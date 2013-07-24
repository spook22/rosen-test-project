package com.softwareag.eda.nerv.connection;

import com.softwareag.eda.nerv.NERVException;

public class NERVConnectionFactory {

	private static final String createDefaultConnectionProperty = "create.default.connection";

	private static NERVConnection defaultConnection;

	public static NERVConnection getDefaultConnection() throws NERVException {
		if (defaultConnection == null) {
			if (Boolean.TRUE.toString().equals(System.getProperty(createDefaultConnectionProperty, Boolean.TRUE.toString()))) {
				createDefaultConnection();
			} else {
				throw new NERVException("Default connection has not been set. Make sure NERV has been properly initialized.");
			}
		}
		return defaultConnection;
	}

	private static synchronized void createDefaultConnection() {
		if (defaultConnection == null) {
			setDefaultConnection(new VMConnection());
		}
	}

	protected static synchronized void setDefaultConnection(NERVConnection connection) throws NERVException {
		if (defaultConnection != null) {
			throw new NERVException("Default connection has already been set.");
		}
		defaultConnection = connection;
	}

}
