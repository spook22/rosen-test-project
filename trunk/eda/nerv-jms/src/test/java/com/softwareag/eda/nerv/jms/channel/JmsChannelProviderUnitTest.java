package com.softwareag.eda.nerv.jms.channel;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;
import com.softwareag.eda.nerv.jms.channel.JmsChannelProvider;

public class JmsChannelProviderUnitTest {

	@Test
	public void testChannel() {
		String internalType = JmsChannelProvider.INTERNAL_NAMESPACE_PREFIX + "/WebM/Test";
		String delimiter = JmsChannelProvider.DEFAULT_DELIMITER;
		ComponentNameProvider componentNameProvider = new DefaultComponentNameProvider("nervDefaultJms");
		JmsChannelProvider provider = new JmsChannelProvider(componentNameProvider);
		String expectedChannel = componentNameProvider + ":topic:" + "Event" + delimiter + "WebM" + delimiter + "Test";
		String channel = provider.channel(internalType);
		assertEquals(expectedChannel, channel);
	}

}
