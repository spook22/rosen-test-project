package com.softwareag.eda.nerv.channel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StaticChannelProviderUnitTest {

	@Test
	public void testChannel() {
		String channel = "myType";
		String type = "myType";
		assertEquals(channel, new StaticChannelProvider(channel).channel(type));
	}

}
