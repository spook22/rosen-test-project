package com.softwareag.eda.nerv.event;

public enum Header {

	START("Start"), TYPE("Type"), EVENT_ID("EventId");

	private final String name;

	private Header(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
