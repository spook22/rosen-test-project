package com.softwareag.eda.nerv.subscribe.subscription;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.camel.Processor;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.MulticastDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;

public class MulticastRoute extends AbstractChannelRoute {

	private final Set<Processor> processors = Collections.synchronizedSet(new HashSet<Processor>());

	private final AtomicBoolean initialized = new AtomicBoolean(false);

	public MulticastRoute(String channel, Processor processor) {
		super(channel);
		addProcessor(processor);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void checkInitialized() throws Exception {
		if (initialized.compareAndSet(false, true)) {
			// Set the CamelContext ErrorHandler here
			ModelCamelContext camelContext = getContext();
			if (camelContext.getErrorHandlerBuilder() != null) {
				setErrorHandlerBuilder(camelContext.getErrorHandlerBuilder());
			}
			configure();
			// mark all route definitions as custom prepared because
			// a route builder prepares the route definitions correctly already
			for (RouteDefinition route : getRouteCollection().getRoutes()) {
				route.markPrepared();
			}
		}
	}

	@Override
	public void configure() throws Exception {
		MulticastDefinition multicast = from(channel).multicast().parallelProcessing().timeout(2000);
		ProcessorDefinition<?> current = multicast;
		// current.process(processors.iterator().next());
		for (Processor processor : processors) {
			current = current.process(processor);
		}
		multicast.end();
	}

	public void addProcessor(Processor processor) {
		processors.add(processor);
		getRouteCollection().getRoutes().clear();
		initialized.set(false);
	}

	public void removeProcessor(Processor processor) {
		processors.remove(processor);
		getRouteCollection().getRoutes().clear();
		initialized.set(false);
	}

	public boolean isEmpty() {
		return processors.isEmpty();
	}

}
