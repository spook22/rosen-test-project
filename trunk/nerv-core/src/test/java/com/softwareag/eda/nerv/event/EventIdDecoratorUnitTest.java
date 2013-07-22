package com.softwareag.eda.nerv.event;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EventIdDecoratorUnitTest {

	@Test
	public void testDecorate() {
		EventDecorator decorator = new EventIdDecorator();
		Event event = new Event("test", "test");
		assertNull(event.getHeader(Header.EVENT_ID));
		decorator.decorate(event);
		assertNotNull(event.getHeader(Header.EVENT_ID));
	}

}
