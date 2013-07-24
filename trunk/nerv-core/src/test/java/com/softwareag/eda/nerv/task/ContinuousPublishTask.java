package com.softwareag.eda.nerv.task;

import org.apache.log4j.Logger;

import com.softwareag.eda.nerv.NERV;

public class ContinuousPublishTask implements Runnable {

	private static final Logger logger = Logger.getLogger(ContinuousPublishTask.class);

	private final String type;

	private final Object message;

	private final int timeout;

	private boolean running = true;

	private long publishedMessages;

	public ContinuousPublishTask(String type, Object message) {
		this(type, message, 500);
	}

	public ContinuousPublishTask(String type, Object message, int timeout) {
		this.type = type;
		this.message = message;
		this.timeout = timeout;
	}

	@Override
	public void run() {
		while (running) {
			NERV.instance().getDefaultConnection().publish(type, message);
			publishedMessages++;
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				logger.debug("Thread sleep interrupted.", e);
			}
		}
	}

	public void stop() {
		this.running = false;
	}

	public long getPublishedMessages() {
		return publishedMessages;
	}

}