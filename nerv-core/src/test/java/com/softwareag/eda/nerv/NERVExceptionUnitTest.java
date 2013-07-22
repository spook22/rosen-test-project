package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class NERVExceptionUnitTest {

	private final String message = "testMessage";

	private final RuntimeException cause = new RuntimeException();

	@Test
	public void testNERVException() {
		NERVException e = new NERVException();
		assertNull(e.getMessage());
		assertNull(e.getCause());
	}

	@Test
	public void testNERVExceptionStringThrowable() {
		NERVException e = new NERVException(message, cause);
		assertEquals(message, e.getMessage());
		assertEquals(cause, e.getCause());
	}

	@Test
	public void testNERVExceptionString() {
		NERVException e = new NERVException(message);
		assertEquals(message, e.getMessage());
	}

	@Test
	public void testNERVExceptionThrowable() {
		NERVException e = new NERVException();
		assertEquals(cause, e.getCause());
	}

}
