package com.softwareag;

public class Measurement {

	private final long timestamp;

	private final String testName;

	private final int eventsCount;

	private final long milliseconds;

	public Measurement(long timestamp, String testName, int eventsCount, long milliseconds) {
		super();
		this.timestamp = timestamp;
		this.testName = testName;
		this.eventsCount = eventsCount;
		this.milliseconds = milliseconds;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getTestName() {
		return testName;
	}

	public int getEventsCount() {
		return eventsCount;
	}

	public long getMilliseconds() {
		return milliseconds;
	}

	@Override
	public String toString() {
		return timestamp + "," + testName + "," + eventsCount + "," + milliseconds + "," + (eventsCount / milliseconds / 1000);
	}

}
