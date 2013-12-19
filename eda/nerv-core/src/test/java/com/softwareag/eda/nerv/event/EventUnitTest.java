package com.softwareag.eda.nerv.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.softwareag.eda.nerv.NERVException;

public class EventUnitTest {

	private final String type = "testType";

	private final String body = "testBody";

	private final Event event = new Event(type, body);

	@Test
	public void testEventMapOfStringObjectObject() {
		String expectedId = "testId";
		String expectedType = "testEventMapOfStringObjectObjectType";
		String body = "testEventMapOfStringObjectObjectBody";

		Map<String, Object> headers = new HashMap<>();
		headers.put(Header.EVENT_ID.getName(), expectedId);
		headers.put(Header.TYPE.getName(), expectedType);
		Event customEvent = new Event(headers, body);

		assertEquals(expectedId, customEvent.getHeader(Header.EVENT_ID));
		assertNotEquals(headers, event.getHeaders());
	}

	@Test(expected = NERVException.class)
	public void testEventMissingType() {
		new Event((String) null, body);
	}

	@Test(expected = NERVException.class)
	public void testEventMissingBody() {
		new Event(type, null);
	}

	@Test(expected = NERVException.class)
	public void testEventMapMissingType() {
		new Event(new HashMap<String, Object>(), "test");
	}

	@Test(expected = NERVException.class)
	public void testEventMapMissingBody() {
		Map<String, Object> headers = new HashMap<>();
		headers.put(Header.TYPE.getName(), type);
		new Event(headers, null);
	}

	@Test(expected = NERVException.class)
	public void testEventNullHeaders() {
		new Event((Map<String, Object>) null, "test");
	}

	@Test
	public void testGetHeaders() {
		Map<String, Object> headers = event.getHeaders();
		assertNotNull(headers);
		assertEquals(type, headers.get(Header.TYPE.getName()));
	}

	@Test
	public void testGetBody() {
		assertEquals(body, event.getBody());
	}

	@Test
	public void testGetType() {
		assertEquals(type, event.getType());
	}

	@Test
	public void testGetHeaderHeader() {
		assertEquals(type, event.getHeader(Header.TYPE));
	}

	@Test
	public void testGetHeaderString() {
		assertEquals(type, event.getHeader(Header.TYPE.getName()));
	}

	@Test
	public void testGetHeaderAsStr() {
		assertEquals(type, event.getHeaderAsStr(Header.TYPE));
	}

	@Test
	public void testSetHeader() {
		String expectedId = "testId";
		event.setHeader(Header.EVENT_ID, expectedId);
		assertEquals(expectedId, event.getHeader(Header.EVENT_ID));
	}

	@Test
	public void testToString() {
		String eventAsString = event.toString();
		assertEquals("Event [headers=" + event.getHeaders() + ", body=" + event.getBody() + "]", eventAsString);
	}

}
