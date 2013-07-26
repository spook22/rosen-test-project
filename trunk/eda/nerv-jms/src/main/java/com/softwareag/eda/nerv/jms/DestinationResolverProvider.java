package com.softwareag.eda.nerv.jms;

import org.springframework.jms.support.destination.DestinationResolver;

public interface DestinationResolverProvider {

	DestinationResolver destinationResolver();

}
