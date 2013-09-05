package com.softwareag.eda.nerv.jms;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.destination.JndiDestinationResolver;

public class DefaultDestinationResolver extends JndiDestinationResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultDestinationResolver.class);

    @Override
    protected Object lookup(String jndiName) throws NamingException {
        return lookup(jndiName, null);
    }

    @Override
    protected <T> T lookup(String jndiName, Class<T> requiredType) throws NamingException {
        T object = null;
        Thread currentThread = Thread.currentThread();
        ClassLoader currentLoader = currentThread.getContextClassLoader();
//        currentThread.setContextClassLoader(this.classLoader);
        try {
            object = super.lookup(jndiName, requiredType);
        } catch (NamingException ne) {
            try {
                bindTopic(jndiName);
                object = super.lookup(jndiName, requiredType);
            } catch (Exception e) {
                logger.error("Cannot bind JNDI name: " + jndiName, e);
            }
        } finally {
            currentThread.setContextClassLoader(currentLoader);
        }
        createTopic(jndiName);
        return object;
    }

	private void createTopic(String jndiName) {
		// TODO Auto-generated method stub
		
	}

	private void bindTopic(String jndiName) {
		// TODO Auto-generated method stub
		
	}

}
