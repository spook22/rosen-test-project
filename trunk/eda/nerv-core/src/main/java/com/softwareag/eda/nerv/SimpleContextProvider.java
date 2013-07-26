package com.softwareag.eda.nerv;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultCamelContextNameStrategy;
import org.apache.camel.spi.CamelContextNameStrategy;

public class SimpleContextProvider implements ContextProvider {

	private final CamelContext context;

	public SimpleContextProvider() {
		this.context = createContext();
	}

	public SimpleContextProvider(CamelContext context) {
		this.context = context;
	}

	private CamelContext createContext() {
		CamelContext context = new DefaultCamelContext();
		CamelContextNameStrategy nameStrategy = new DefaultCamelContextNameStrategy("com.softwareag.eda.nerv.default.context");
		context.setNameStrategy(nameStrategy);
		context.getShutdownStrategy().setShutdownNowOnTimeout(true);
		return context;
	}

	@Override
	public CamelContext context() {
		return context;
	}

}
