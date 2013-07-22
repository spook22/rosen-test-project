package com.softwareag.eda.nerv;

import static org.powermock.api.easymock.PowerMock.createMockAndExpectNew;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import org.apache.camel.impl.DefaultCamelContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NERV.class)
public class NERVDirectChannelUnitTest extends NERVUnitTest {

	private static String prevChannelType;

	@BeforeClass
	public static void beforeClass() throws Exception {
		prevChannelType = System.setProperty(NERV.PROP_CHANNEL_TYPE, NERV.PROP_CHANNEL_TYPE_DIRECT);
		NERV.destroy();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		if (prevChannelType != null) {
			System.setProperty(NERV.PROP_CHANNEL_TYPE, prevChannelType);
		} else {
			System.getProperties().remove(NERV.PROP_CHANNEL_TYPE);
		}
		NERV.destroy();
	}

	@Test(expected = NERVException.class)
	public void testGetInstanceCannotStartContext() throws Exception {
		DefaultCamelContext context = createMockAndExpectNew(DefaultCamelContext.class);
		context.start();
		expectLastCall().andThrow(new Exception("This is a test exception created using PowerMock."));
		replay(context, DefaultCamelContext.class);
		NERV.destroy();
		NERV.instance();
		verify(context, DefaultCamelContext.class);
		NERV.destroy(); // This is needed so other tests don't fail because of the mocking.
	}
}
