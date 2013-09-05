package com.softwareag.eda.nerv.jms.support;

import javax.jms.ConnectionFactory;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiTemplate;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.jms.ConnectionFactoryProvider;

abstract class AbstractDestinationSupport extends JndiDestinationResolver implements ConnectionFactoryProvider {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDestinationSupport.class);

	protected final String providerUrl;

	private final JndiTemplate jndiTemplate;

	private ClassLoader classLoader;

	private ConnectionFactory connectionFactory;

	private String connectionFactoryName;

	public AbstractDestinationSupport(String providerUrl) {
		this.providerUrl = providerUrl;
		jndiTemplate = new JndiTemplate(null);
		initializeConfiguration();
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	protected abstract ConnectionFactory createConnectionFactory();

	public abstract void bindTopic(String jndiEntryName) throws NERVException;

	public abstract void createTopic(String jndiEntryName);

	private void initializeConfiguration() {
		initializeProviderSpecifics();
		initializeConnectionFactory();
	}

	protected abstract void initializeProviderSpecifics();

	private void initializeConnectionFactory() {
		connectionFactory = retrieveConnectionFactory();
	}

	@Override
	public ConnectionFactory connectionFactory() {
		return connectionFactory;
	}

	public void createAndBindConnectionFactory() {
		try {
			lookupConnectionFactory();
			if (logger.isDebugEnabled()) {
				logger.debug("ConnectionFactory with name '" + connectionFactoryName
						+ "' is already bound to JMS Provider at '" + providerUrl + "'.");
			}
		} catch (NamingException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("'EventFactory' is not bound to JNDI registry at '" + providerUrl
						+ "' - NERV will try to bind it.", e);
			}
			try {
				bind(connectionFactoryName, connectionFactory());
			} catch (NamingException ne) {
				throw new NERVException("Unable to bind connection factory with name '" + connectionFactoryName
						+ "' to JNDI registry at '" + providerUrl + "'.", ne);
			}
		}
	}

	private ClassLoader pushClassLoader() {
		Thread currentThread = Thread.currentThread();
		ClassLoader currentLoader = currentThread.getContextClassLoader();
		if (classLoader != null) {
			currentThread.setContextClassLoader(classLoader);
		}
		return currentLoader;
	}

	private void popClassLoader(ClassLoader classLoader) {
		Thread.currentThread().setContextClassLoader(classLoader);
	}

	protected synchronized void bind(String jndiName, Object object) throws NamingException {
		ClassLoader currentLoader = pushClassLoader();
		try {
			jndiTemplate.bind(jndiName, object);
			if (logger.isInfoEnabled()) {
				logger.info("Object with entry name '" + jndiName + "' successfully bound to JNDI registry at '"
						+ providerUrl + "'.");
			}
		} catch (NameAlreadyBoundException ne) {
			if (logger.isDebugEnabled()) {
				logger.debug("Object with entry name '" + jndiName + "' is already bound to JNDI registry at '"
						+ providerUrl + "': " + ne.getMessage());
			}
		} finally {
			popClassLoader(currentLoader);
		}
	}

	private ConnectionFactory retrieveConnectionFactory() {
		try {
			return lookup(connectionFactoryName, ConnectionFactory.class);
		} catch (NamingException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("EventFactory is not bound to JNDI registry at '" + providerUrl + "'.", e);
			}
			return createConnectionFactory();
		}
	}

    @Override
    protected Object lookup(String jndiName) throws NamingException {
        return lookup(jndiName, null);
    }

    @Override
    protected <T> T lookup(String jndiName, Class<T> requiredType) throws NamingException {
        T object = null;
		ClassLoader currentLoader = pushClassLoader();
        try {
            object = super.lookup(jndiName, requiredType);
//			object = jndiTemplate.lookup(jndiName, requiredType);
        } catch (NamingException ne) {
            try {
                bindTopic(jndiName);
                object = super.lookup(jndiName, requiredType);
            } catch (Exception e) {
                logger.error("Cannot bind JNDI name: " + jndiName, e);
            }
        } finally {
			popClassLoader(currentLoader);
        }
        createTopic(jndiName);
        return object;
    }

	private ConnectionFactory lookupConnectionFactory() throws NamingException {
		return lookup(connectionFactoryName, ConnectionFactory.class);
	}
	
}
