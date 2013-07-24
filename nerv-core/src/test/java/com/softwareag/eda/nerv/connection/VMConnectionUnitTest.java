package com.softwareag.eda.nerv.connection;

import org.junit.Test;

import com.softwareag.eda.nerv.TestHelper;
import com.softwareag.eda.nerv.channel.StaticChannelProvider;

public class VMConnectionUnitTest {

	@Test
	public void testChannelPublishSubscribe() throws Exception {
		String channel = "vm:myChannel";
		VMConnection connection = new VMConnection(new StaticChannelProvider(channel));
		TestHelper.testConnection(connection, channel, 2);
	}

}
