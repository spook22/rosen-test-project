package com.softwareag.eda.nerv.subscribe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.softwareag.eda.nerv.consumer.BasicConsumer;

public class DefaultEventProcessorUnitTest {

	@Test
	public void testEqualsObject() {
		BasicConsumer consumer = new BasicConsumer();
		DefaultEventProcessor first = new DefaultEventProcessor(consumer);
		DefaultEventProcessor second = new DefaultEventProcessor(consumer);
		assertTrue(first.equals(second));

		DefaultEventProcessor third = new DefaultEventProcessor(new BasicConsumer());
		assertFalse(first.equals(third));

		assertFalse(first.equals(consumer));
	}
}
