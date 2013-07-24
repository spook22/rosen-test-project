package com.softwareag.eda.nerv;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.softwareag.eda.nerv.connection.NERVConnection;
import com.softwareag.eda.nerv.help.SystemPropertyChanger;
import com.softwareag.eda.nerv.help.TestHelper;

public class NERVConnectionFactoryUnitTest {

	private static SystemPropertyChanger propertyChanger = new SystemPropertyChanger(NERVConnectionFactory.PROP_CREATE_DEFAULT_CONNECTION);

	@Test
	public void testNERVConnectionFactory() {
		NERVConnectionFactory factory = new NERVConnectionFactory(); // Not needed, just increasing test coverage.
		assertNotNull(factory);
	}

	@Test
	public void testGetDefaultConnection() throws Exception {
		propertyChanger.change(Boolean.TRUE.toString());
		try {
			NERVConnection connection = NERVConnectionFactory.getDefaultConnection();
			assertNotNull(connection);
			TestHelper.testConnection(connection, null, 5);
			connection = NERVConnectionFactory.getDefaultConnection();
			assertNotNull(connection);
			TestHelper.testConnection(connection, null, 5);
		} finally {
			propertyChanger.revert();
		}
	}

	@Test(expected = NERVException.class)
	public void testGetDefaultConnectionNotInitialized() throws Exception {
		propertyChanger.change(Boolean.FALSE.toString());
		try {
			NERVConnectionFactory.destroyDefaultConnection();
			NERVConnectionFactory.getDefaultConnection();
		} finally {
			propertyChanger.revert();
		}
	}

	@Test(expected = NERVException.class)
	public void testSetDefaultConnectionAlreadySet() {
		NERVConnectionFactory.getDefaultConnection();
		NERVConnectionFactory.setDefaultConnection(null);
	}

	@Test(expected = NERVException.class)
	public void testDestroyDefaultConnection() {
		propertyChanger.change(Boolean.TRUE.toString());
		try {
			NERVConnection connection = NERVConnectionFactory.getDefaultConnection();
			assertNotNull(connection);
		} finally {
			propertyChanger.revert();
		}
		propertyChanger.change(Boolean.FALSE.toString());
		try {
			NERVConnectionFactory.destroyDefaultConnection();
			NERVConnectionFactory.getDefaultConnection();
		} finally {
			propertyChanger.revert();
		}
	}

	@Test
	public void testCreateChannelConnection() throws Exception {
		String channel = "vm:testCreateChannelConnection";
		NERVConnection connection = NERVConnectionFactory.createChannelConnection(channel);
		TestHelper.testConnection(connection, channel, 5);
	}

}
