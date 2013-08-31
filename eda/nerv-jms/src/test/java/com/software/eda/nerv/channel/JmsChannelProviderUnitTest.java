package com.software.eda.nerv.channel;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.softwareag.eda.nerv.channel.JmsChannelProvider;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;

public class JmsChannelProviderUnitTest {

	@Test
	public void test() {
		String internalType = JmsChannelProvider.INTERNAL_NAMESPACE_PREFIX + "/WebM/Test";
		String delimiter = JmsChannelProvider.DEFAULT_DELIMITER;
		ComponentNameProvider componentNameProvider = new DefaultComponentNameProvider("nervDefaultJms");
		JmsChannelProvider provider = new JmsChannelProvider(componentNameProvider);
		String expectedChannel = componentNameProvider + ":topic:" + "Event" + delimiter + "WebM" + delimiter + "Test";
		String channel = provider.channel(internalType);
		assertEquals(expectedChannel, channel);
	}

}
