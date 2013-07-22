package com.softwareag.eda.nerv;

import org.apache.camel.CamelContext;

public class SimpleContextProvider implements ContextProvider {

	private final CamelContext context;

	public SimpleContextProvider(CamelContext context) {
		this.context = context;
	}

	@Override
	public CamelContext context() {
		return context;
	}

}
