package com.softwareag.eda.nerv.properties;

import java.util.Properties;

public class NERVPropertiesProvider {

	public static final String DEFAULT_JMS_PROVIDER_URL = "nerv.default.jms.provider.url";

	public static final String DEFAULT_JMS_COMPONENT_NAME = "nerv.default.jms.component.name";

	private static NERVPropertiesProvider instance;

	public static NERVPropertiesProvider instance() {
		if (instance == null) {
			synchronized (NERVPropertiesProvider.class) {
				if (instance == null) {
					instance = new NERVPropertiesProvider();
				}
			}
		}
		return instance;
	}

	private final Properties props = new Properties();

	private final Properties defaultValues = new Properties();

	private NERVPropertiesProvider() {
		defaultValues.setProperty(DEFAULT_JMS_PROVIDER_URL, "nsp://localhost:9000");
		defaultValues.setProperty(DEFAULT_JMS_COMPONENT_NAME, "nervDefaultJms");
	}

	public String getProperty(String key) {
		return props.getProperty(key, System.getProperty(key, getDefaultValue(key)));
	}

	public String setProperty(String key, String value) {
		return (String) props.setProperty(key, value);
	}

	private String getDefaultValue(String key) {
		return defaultValues.getProperty(key);
	}

}
