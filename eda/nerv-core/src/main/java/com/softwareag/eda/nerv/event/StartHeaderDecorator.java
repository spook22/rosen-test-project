package com.softwareag.eda.nerv.event;

import java.util.Date;

public class StartHeaderDecorator extends AbstractEventDecorator {

	public StartHeaderDecorator() {
	}

	public StartHeaderDecorator(EventDecorator decorator) {
		super(decorator);
	}

	@Override
	public void decorate(Event event) {
		super.decorate(event);
		String start = event.getHeaderAsStr(Header.START);
		if (start == null || start.isEmpty()) {
			event.setHeader(Header.START, new Date().toString());
		}
	}

}
