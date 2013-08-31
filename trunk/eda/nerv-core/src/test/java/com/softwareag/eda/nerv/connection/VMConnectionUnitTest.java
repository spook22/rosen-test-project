package com.softwareag.eda.nerv.connection;

import org.junit.Test;

import com.softwareag.eda.nerv.SimpleContextProvider;
import com.softwareag.eda.nerv.channel.StaticChannelProvider;
import com.softwareag.eda.nerv.component.SpringComponentResolver;
import com.softwareag.eda.nerv.help.TestHelper;

public class VMConnectionUnitTest {

	@Test
	public void testChannelPublishSubscribe() throws Exception {
		String channel = "vm:testChannelPublishSubscribe";
		VMConnection connection = new VMConnection(new SimpleContextProvider(), new StaticChannelProvider(channel),
				new SpringComponentResolver());
		TestHelper.testConnection(connection, channel, 2);
	}

}
