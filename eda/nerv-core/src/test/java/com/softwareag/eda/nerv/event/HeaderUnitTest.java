package com.softwareag.eda.nerv.event;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HeaderUnitTest {

	@Test
	public void testGetName() {
		Header startHeader = Header.valueOf("START");
		for (Header header : Header.values()) {
			if (header.equals(startHeader)) {
				assertEquals("Start", header.getName());
			}
		}
	}

}
