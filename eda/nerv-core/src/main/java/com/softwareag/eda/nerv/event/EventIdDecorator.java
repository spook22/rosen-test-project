package com.softwareag.eda.nerv.event;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.subscribe.handler.AbstractSubscriptionHandler;

public class EventIdDecorator extends AbstractEventDecorator {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractSubscriptionHandler.class);

	public EventIdDecorator() {
	}

	public EventIdDecorator(EventDecorator decorator) {
		super(decorator);
	}

	@Override
	public void decorate(Event event) {
		super.decorate(event);
		Header header = Header.EVENT_ID;
		String eventId = event.getHeaderAsStr(header);
		if (eventId == null || eventId.isEmpty()) {
			eventId = UUID.randomUUID().toString();
			event.setHeader(Header.EVENT_ID, eventId);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Decorated event with %s %s.", header, eventId));
			}
		}
	}

}
