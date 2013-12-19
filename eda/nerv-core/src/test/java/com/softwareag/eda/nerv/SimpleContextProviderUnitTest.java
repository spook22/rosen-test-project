package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import com.softwareag.eda.nerv.context.SimpleContextProvider;

public class SimpleContextProviderUnitTest {

	@Test
	public void testSimpleContextProvider() {
		CamelContext context = new SimpleContextProvider().context();
		assertNotNull(context);
		String name = context.getName();
		assertTrue(name.startsWith(SimpleContextProvider.NAME_TEMPLATE));
	}

	@Test
	public void testSimpleContextProviderCamelContext() {
		CamelContext expected = new DefaultCamelContext();
		CamelContext actual = new SimpleContextProvider(expected).context();
		assertNotNull(actual);
		assertEquals(expected, actual);
		String name = actual.getName();
		assertFalse(name.startsWith(SimpleContextProvider.NAME_TEMPLATE));
	}

}
