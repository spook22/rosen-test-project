package com.softwareag.eda.nerv.channel;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VMChannelProviderUnitTest {

	@Test
	public void testChannel() {
		String type = "myType";
		assertTrue(new VMChannelProvider().channel(type).startsWith("vm://" + type));
	}

}
