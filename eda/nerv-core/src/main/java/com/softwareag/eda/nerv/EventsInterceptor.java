package com.softwareag.eda.nerv;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EventsInterceptor implements Processor {

	private static EventsInterceptor instance;

	public static EventsInterceptor instance() {
		if (instance == null) {
			synchronized (NERV.class) {
				if (instance == null) {
					instance = new EventsInterceptor();
				}
			}
		}
		return instance;
	}

	private final Collection<Processor> interceptors = new ArrayList<>();

	public EventsInterceptor() {
		super();
	}

	public void registerInterceptor(Processor interceptor) {
		interceptors.add(interceptor);
	}

	public boolean unregisterInterceptor(Processor interceptor) {
		return interceptors.remove(interceptor);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		for (Processor interceptor : interceptors) {
			interceptor.process(exchange);
		}
	}

}
