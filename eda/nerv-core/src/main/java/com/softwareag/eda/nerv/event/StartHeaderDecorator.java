package com.softwareag.eda.nerv.event;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartHeaderDecorator extends AbstractEventDecorator {
	
	private static final Logger logger = LoggerFactory.getLogger(StartHeaderDecorator.class);

	public StartHeaderDecorator() {
	}

	public StartHeaderDecorator(EventDecorator decorator) {
		super(decorator);
	}

	@Override
	public void decorate(Event event) {
		super.decorate(event);
		Header header = Header.START;
		String start = event.getHeaderAsStr(header);
		if (start == null || start.isEmpty()) {
			start = new Date().toString();
			event.setHeader(Header.START, start);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Decorated event with %s %s.", header, start));
			}
		}
	}

}
