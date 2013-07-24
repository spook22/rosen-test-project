package com.softwareag.eda.nerv;

import static org.powermock.api.easymock.PowerMock.createMockAndExpectNew;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.resetAll;
import static org.powermock.api.easymock.PowerMock.verify;

import org.apache.camel.impl.DefaultCamelContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.softwareag.eda.nerv.help.SystemPropertyChanger;

@RunWith(PowerMockRunner.class)
// @PrepareForTest(NERV.class)
public class NERVDirectChannelUnitTest extends NERVUnitTest {

	private static SystemPropertyChanger propertyChanger = new SystemPropertyChanger(NERV.PROP_CHANNEL_TYPE);

	@BeforeClass
	public static void beforeClass() throws Exception {
		propertyChanger.change(NERV.PROP_CHANNEL_TYPE_DIRECT);
		NERV.destroy();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		propertyChanger.revert();
		NERV.destroy();
		NERV.destroy(); // Make sure nothing happens. Increase test coverage.
	}

	@Ignore
	@Test(expected = NERVException.class)
	public void testGetInstanceCannotStartContext() throws Exception {
		DefaultCamelContext context = createMockAndExpectNew(DefaultCamelContext.class);
		context.start();
		expectLastCall().andThrow(new Exception("This is a test exception created using PowerMock."));
		replay(context, DefaultCamelContext.class);
		NERV.destroy();
		NERV.instance();
		verify(context, DefaultCamelContext.class);
		resetAll();
	}
}
