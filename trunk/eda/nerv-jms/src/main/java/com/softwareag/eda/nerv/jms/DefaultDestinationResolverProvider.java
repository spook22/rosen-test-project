package com.softwareag.eda.nerv.jms;

import org.springframework.jms.support.destination.DestinationResolver;

public class DefaultDestinationResolverProvider implements DestinationResolverProvider {

	@Override
	public DestinationResolver destinationResolver() {
		return new DefaultDestinationResolver();
	}

}
