package com.softwareag.eda.nerv;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class NERVDirectChannelUnitTest extends NERVUnitTest {

	private static String prevChannelType;

	@BeforeClass
	public static void beforeClass() throws Exception {
		prevChannelType = System.setProperty(NERV.PROP_CHANNEL_TYPE, NERV.PROP_CHANNEL_TYPE_DIRECT);
		NERV.setInstance(new NERV());
	}

	@AfterClass
	public static void afterClass() throws Exception {
		if (prevChannelType != null) {
			System.setProperty(NERV.PROP_CHANNEL_TYPE, prevChannelType);
		} else {
			System.getProperties().remove(NERV.PROP_CHANNEL_TYPE);
		}
		NERV.setInstance(new NERV());
	}
}
