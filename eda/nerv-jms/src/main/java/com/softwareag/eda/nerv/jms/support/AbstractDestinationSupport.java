package com.softwareag.eda.nerv.jms.support;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.destination.JndiDestinationResolver;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.jms.ConnectionFactoryProvider;

abstract class AbstractDestinationSupport extends JndiDestinationResolver implements ConnectionFactoryProvider {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDestinationSupport.class);

	protected final String providerUrl;

	private ClassLoader classLoader;

	private final String connectionFactoryName = "EventFactory";

	private ConnectionFactory connectionFactory;

	public AbstractDestinationSupport(String providerUrl) {
		this.providerUrl = providerUrl;

		Properties env = new Properties();
		env.put(Context.PROVIDER_URL, providerUrl);
		env.put("connectionFactory", connectionFactoryName);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.pcbsys.nirvana.nSpace.NirvanaContextFactory");
		env.put("nirvana.setReadThreadDaemon", "true");

		setJndiEnvironment(env);
		initializeConfiguration();
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public ConnectionFactory connectionFactory() {
		return connectionFactory;
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
			super.getJndiTemplate().bind(jndiName, object);
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
			ConnectionFactory connectionFactory = createConnectionFactory();
			try {
				bind(connectionFactoryName, connectionFactory);
			} catch (NamingException e1) {
				throw new NERVException("Cannot bind connection factory: " + connectionFactoryName, e1);
			}
			return connectionFactory;
		}
	}

	@Override
	protected Object lookup(String jndiName) throws NamingException {
		return lookup(jndiName, null);
	}

	@Override
	protected <T> T lookup(String jndiName, Class<T> requiredType) throws NamingException {
		ClassLoader currentLoader = pushClassLoader();
		try {
			return super.lookup(jndiName, requiredType);
		} 
//		catch (NamingException ne) {
//			try {
//				createTopic(jndiName);
//				bindTopic(jndiName);
//				object = super.lookup(jndiName, requiredType);
//			} catch (Exception e) {
//				logger.error("Cannot bind JNDI name: " + jndiName, e);
//			}
//		}
		finally {
			popClassLoader(currentLoader);
		}
	}

}
