package com.softwareag.eda.nerv.task;

import com.softwareag.eda.nerv.NERV;

public class PublishTask implements Runnable {

	private final String type;

	private final Object message;

	private final int count;

	public PublishTask(String type, Object message, int count) {
		this.type = type;
		this.message = message;
		this.count = count;
	}

	@Override
	public void run() {
		for (int index = 0; index < count; index++) {
			NERV.instance().getDefaultConnection().publish(type, message);
		}
	}

}