package com.softwareag.eda.nerv.jms.intercept;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softwareag.eda.nerv.intercept.EventsInterceptor;
import com.softwareag.eda.nerv.route.RouteCreator;

public class JmsSupportInterceptor implements Processor {
	
	private static final Logger logger = LoggerFactory.getLogger(JmsSupportInterceptor.class);
	
	private final RouteCreator routeCreator;

	public JmsSupportInterceptor(RouteCreator routeCreator) {
		super();
		this.routeCreator = routeCreator;
		EventsInterceptor.instance().registerInterceptor(this);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Intercepted an event: " + exchange.getIn());
		}
	}

}
