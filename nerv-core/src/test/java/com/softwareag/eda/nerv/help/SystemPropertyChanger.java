package com.softwareag.eda.nerv.help;

public class SystemPropertyChanger {

	private final String property;

	public SystemPropertyChanger(String property) {
		this.property = property;
	}

	private String prevValue;

	public synchronized String change(String value) {
		prevValue = System.setProperty(property, value);
		return prevValue;
	}

	public synchronized void revert() {
		if (prevValue != null) {
			System.setProperty(property, prevValue);
		} else {
			System.getProperties().remove(property);
		}
	}

}
