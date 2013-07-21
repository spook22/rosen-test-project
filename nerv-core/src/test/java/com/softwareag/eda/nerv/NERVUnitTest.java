package com.softwareag.eda.nerv;

import org.junit.Test;

public class NERVUnitTest extends AbstractNERVUnitTest {

	@Test
	public void testPubSub() throws Exception {
		pubSub(1);
	}

	@Test
	public void testPubSub100kMsgs() throws Exception {
		pubSub(100000);
	}

	@Test
	public void testPubSub100kMsgs10Threads() throws Exception {
		pubSub(100000, 1, 10);
	}

}
