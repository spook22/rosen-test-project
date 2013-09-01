package com.softwareag.eda.nerv;

import java.io.InputStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.file.FileUtils;

public class NERV1MbMsgUnitTest extends AbstractNERVUnitTest {

	private static final Logger logger = LoggerFactory.getLogger(NERV1MbMsgUnitTest.class);

	private static final String NEW_LINE = System.getProperty("line.separator");

	private static final String resource = "1MB.xml";

	private static String testMessage;

	@BeforeClass
	public static void beforeClass() throws Exception {
		InputStream input = NERV1MbMsgUnitTest.class.getResourceAsStream(resource);
		try {
			testMessage = FileUtils.read(input);
			logger.debug("Loaded test message:" + NEW_LINE + testMessage);
		} finally {
			input.close();
		}
	}

	@Override
	@Before
	public void before() throws Exception {
		super.before();
		body = testMessage;
	}

	@Override
	@Test
	public void testPubSub() throws Exception {
		pubSub(1);
	}

	@Test
	public void testPubSub10kMsgs() throws Exception {
		pubSub(10000);
	}

	@Ignore
	@Test
	public void testPubSub10kMsgs10Threads() throws Exception {
		pubSub(10000, 1, 10);
	}

	@Test
	public void testPubSub10kMsgs2Consumers10Threads() throws Exception {
		pubSub(10000, 2, 10);
	}

}
