package com.softwareag.eda.nerv.event;

import java.util.UUID;

public class EventIdDecorator extends AbstractEventDecorator {

	public EventIdDecorator() {
	}

	public EventIdDecorator(EventDecorator decorator) {
		super(decorator);
	}

	@Override
	public void decorate(Event event) {
		super.decorate(event);
		String eventId = event.getHeaderAsStr(Header.EVENT_ID);
		if (eventId == null || eventId.isEmpty()) {
			eventId = UUID.randomUUID().toString();
			event.setHeader(Header.EVENT_ID, eventId);
		}
	}

}
