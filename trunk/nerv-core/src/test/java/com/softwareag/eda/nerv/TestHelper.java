package com.softwareag.eda.nerv;

import com.softwareag.eda.nerv.consumer.BasicConsumer;

public class TestHelper {

	public static void waitForEvents(BasicConsumer consumer, int eventsCount, int timeout) throws Exception {
		int waitTime = 100;
		int totalWaitTime = 0;
		while (consumer.getEvents().size() < eventsCount && totalWaitTime <= timeout) {
			synchronized (consumer.getLock()) {
				consumer.getLock().wait(waitTime);
			}
			totalWaitTime += waitTime;
		}
	}

}
