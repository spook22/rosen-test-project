package com.softwareag.eda.nerv.event;

public abstract class AbstractEventDecorator implements EventDecorator {

	private final EventDecorator decorator;

	public AbstractEventDecorator() {
		decorator = null;
	}

	public AbstractEventDecorator(EventDecorator decorator) {
		this.decorator = decorator;
	}

	@Override
	public void decorate(Event event) {
		if (decorator != null) {
			decorator.decorate(event);
		}
	}

}
