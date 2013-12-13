package com.softwareag.eda.nerv.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class EventIdDecoratorUnitTest {

	@Test
	public void testDecorate() {
		StartHeaderDecorator startDecorator = new StartHeaderDecorator();
		EventIdHeaderDecorator decorator = new EventIdHeaderDecorator(startDecorator);
		Event event = new Event("type", "body");
		assertNull(event.getHeader(Header.START));
		assertNull(event.getHeader(Header.EVENT_ID));
		decorator.decorate(event);
		assertNotNull(event.getHeader(Header.START));
		assertNotNull(event.getHeader(Header.EVENT_ID));

		decorator = new EventIdHeaderDecorator();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(Header.TYPE.getName(), "type");
		headers.put(Header.EVENT_ID.getName(), "");
		event = new Event(headers, "body");
		assertEquals("", event.getHeader(Header.EVENT_ID));
		decorator.decorate(event);
		assertNotNull(event.getHeader(Header.EVENT_ID));

		String eventId = (String) event.getHeader(Header.EVENT_ID);
		decorator.decorate(event);
		assertEquals(eventId, event.getHeader(Header.EVENT_ID)); // The ID should not have changed.
	}

}
