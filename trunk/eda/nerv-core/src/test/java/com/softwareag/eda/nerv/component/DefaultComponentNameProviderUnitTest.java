package com.softwareag.eda.nerv.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultComponentNameProviderUnitTest {

	@Test
	public void testComponentName() {
		String expected = "testComponentName";
		String actual = new DefaultComponentNameProvider(expected).componentName();
		assertEquals(expected, actual);
	}

}
