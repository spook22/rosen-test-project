package com.softwareag.eda.nerv.channel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DirectChannelProviderUnitTest {

	@Test
	public void testChannel() {
		String type = "myType";
		assertEquals("direct:" + type, new DirectChannelProvider().channel(type));
	}

}
