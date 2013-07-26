package com.softwareag.eda.nerv.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.jms.support.destination.DestinationResolver;

public class DefaultDestinationResolver implements DestinationResolver {

	@Override
	public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

}
