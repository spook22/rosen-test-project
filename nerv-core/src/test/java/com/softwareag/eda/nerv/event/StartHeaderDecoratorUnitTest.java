package com.softwareag.eda.nerv.event;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StartHeaderDecoratorUnitTest {

	@Test
	public void testDecorate() {
		EventDecorator idDecorator = new EventIdDecorator();
		StartHeaderDecorator decorator = new StartHeaderDecorator(idDecorator);
		Event event = new Event("test", "test");
		assertNull(event.getHeader(Header.START));
		assertNull(event.getHeader(Header.EVENT_ID));
		decorator.decorate(event);
		assertNotNull(event.getHeader(Header.START));
		assertNotNull(event.getHeader(Header.EVENT_ID));
	}

}
