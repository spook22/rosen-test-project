package com.softwareag.eda.nerv.channel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.softwareag.eda.nerv.component.ComponentNameProvider;

public class JmsChannelProvider implements ChannelProvider {

	public static final String INTERNAL_NAMESPACE_PREFIX = "http://namespaces.softwareag.com/EDA";

	public static final String DEFAULT_DELIMITER = "::";

	private final Map<String, String> channelsMap = Collections.synchronizedMap(new HashMap<String, String>());

	private final ComponentNameProvider componentNameProvider;

	private String delimiter = DEFAULT_DELIMITER;

	public JmsChannelProvider(ComponentNameProvider componentNameProvider) {
		this.componentNameProvider = componentNameProvider;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public String channel(String type) {
		type = type.trim();
		String channel = channelsMap.get(type);
		channel = map(type);
		return channel;
	}

	private String map(String type) {
		String channel = componentNameProvider + ":topic:" + getJndiEntryName(type);
		channelsMap.put(type, channel);
		return channel;
	}

	private String getJndiEntryName(String type) {
		return "Event" + delimiter + extractPath(type);
	}

	private String extractPath(String type) {
		String prefix = INTERNAL_NAMESPACE_PREFIX;
		if (type.startsWith(prefix)) {
			type = type.substring(prefix.length());
			if (type.startsWith("/")) {
				type = type.substring("/".length());
			}
			return type.replaceAll("/", delimiter);
		} else {
			return "WebM" + delimiter + "External";
		}
	}

}
