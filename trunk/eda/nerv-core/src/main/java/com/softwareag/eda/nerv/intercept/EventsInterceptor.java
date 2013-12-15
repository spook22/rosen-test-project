package com.softwareag.eda.nerv.intercept;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsInterceptor implements Processor {

	private static final Logger logger = LoggerFactory.getLogger(EventsInterceptor.class);

	private static EventsInterceptor instance;

	public static EventsInterceptor instance() {
		if (instance == null) {
			synchronized (EventsInterceptor.class) {
				if (instance == null) {
					instance = new EventsInterceptor();
				}
			}
		}
		return instance;
	}

	private final Collection<Processor> interceptors = new ArrayList<>();

	private EventsInterceptor() {
		super();
	}

	public void registerInterceptor(Processor interceptor) {
		logger.info("Added events interceptor.");
		interceptors.add(interceptor);
	}

	public boolean unregisterInterceptor(Processor interceptor) {
		logger.info("Removed events interceptor.");
		return interceptors.remove(interceptor);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("Intercepted event: " + exchange.getIn());
		}
		for (Processor interceptor : interceptors) {
			interceptor.process(exchange);
		}
	}

}
