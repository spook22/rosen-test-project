package com.softwareag.eda.nerv.publish;

import static org.junit.Assert.fail;

import org.apache.camel.CamelContext;
import org.junit.Test;

import com.softwareag.eda.nerv.ContextProvider;
import com.softwareag.eda.nerv.channel.ChannelProvider;
import com.softwareag.eda.nerv.channel.VMChannelProvider;

public class DefaultPublisherUnitTest {

	private final ContextProvider contextProvider = new ContextProvider() {

		@Override
		public CamelContext context() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private final ChannelProvider channelProvider = new VMChannelProvider();

	@Test
	public void testPublishStringObject() {
		new DefaultPublisher(contextProvider, channelProvider);
	}

	@Test
	public void testPublishMapOfStringObjectObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testPublishEvent() {
		fail("Not yet implemented");
	}

}
