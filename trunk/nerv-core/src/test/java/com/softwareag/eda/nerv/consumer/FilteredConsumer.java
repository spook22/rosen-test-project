package com.softwareag.eda.nerv.consumer;

import org.apache.log4j.Logger;

import com.softwareag.eda.nerv.event.Event;

public class FilteredConsumer extends BasicConsumer {

	private static final Logger logger = Logger.getLogger(FilteredConsumer.class);

	private final String expectedContent;

	public FilteredConsumer() {
		this(null);
	}

	public FilteredConsumer(String expectedContent) {
		this(0, expectedContent);
	}

	public FilteredConsumer(int expectedMessages, String expectedContent) {
		super(expectedMessages);
		this.expectedContent = expectedContent;
	}

	@Override
	public void consume(Event event) {
		if (accept(event.getBody())) {
			super.consume(event);
		} else {
			logger.debug("Skipped message: " + event.getBody());
		}
	}

	private boolean accept(Object content) {
		boolean result = true;
		if (expectedContent != null) {
			result = expectedContent.equals(content);
		}
		return result;
	}

}
