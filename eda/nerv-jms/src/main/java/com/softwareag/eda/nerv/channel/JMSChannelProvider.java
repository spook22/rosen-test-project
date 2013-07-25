package com.softwareag.eda.nerv.channel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.softwareag.eda.nerv.component.ComponentNameProvider;

public class JMSChannelProvider implements ChannelProvider {

	private static String delimiter;

	private final Map<String, String> channelsMap = Collections.synchronizedMap(new HashMap<String, String>());

	private ComponentNameProvider componentNameProvider;

	@Override
	public String channel(String type) {
		type = type.trim();
		String channel = channelsMap.get(type);
		channel = map(type);
		return channel;
	}

	private String map(String type) {
		return componentNameProvider + ":topic:" + getJndiEntryName(type);
	}

	private static String getJndiEntryName(String type) {
		return "Event" + delimiter + extractPath(type);
	}

	private static String extractPath(String type) {
		String prefix = "http://namespaces.softwareag.com/EDA";
		if (type.startsWith(prefix)) {
			return type.substring(prefix.length()).replaceAll("/", delimiter);
		} else {
			return "WebM" + delimiter + "External";
		}
	}

}
