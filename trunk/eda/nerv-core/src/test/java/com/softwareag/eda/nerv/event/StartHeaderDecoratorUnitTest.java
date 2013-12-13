package com.softwareag.eda.nerv.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class StartHeaderDecoratorUnitTest {

	@Test
	public void testDecorate() {
		EventDecorator idDecorator = new EventIdHeaderDecorator();
		StartHeaderDecorator decorator = new StartHeaderDecorator(idDecorator);
		Event event = new Event("type", "body");
		assertNull(event.getHeader(Header.START));
		assertNull(event.getHeader(Header.EVENT_ID));
		decorator.decorate(event);
		assertNotNull(event.getHeader(Header.START));
		assertNotNull(event.getHeader(Header.EVENT_ID));

		decorator = new StartHeaderDecorator();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(Header.TYPE.getName(), "type");
		headers.put(Header.START.getName(), "");
		event = new Event(headers, "body");
		assertEquals("", event.getHeader(Header.START));
		decorator.decorate(event);
		assertNotNull(event.getHeader(Header.START));

		String start = (String) event.getHeader(Header.START);
		decorator.decorate(event);
		assertEquals(start, event.getHeader(Header.START)); // The start header should not have changed.
	}

}
