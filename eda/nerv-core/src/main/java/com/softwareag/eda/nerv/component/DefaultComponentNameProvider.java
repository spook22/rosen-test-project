package com.softwareag.eda.nerv.component;

public class DefaultComponentNameProvider implements ComponentNameProvider {

	private final String componentName;

	public DefaultComponentNameProvider(String componentName) {
		this.componentName = componentName;
	}

	@Override
	public String componentName() {
		return componentName;
	}

}
