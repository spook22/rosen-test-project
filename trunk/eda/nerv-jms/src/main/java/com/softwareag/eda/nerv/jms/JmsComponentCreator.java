package com.softwareag.eda.nerv.jms;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.support.destination.DestinationResolver;

public class JmsComponentCreator {

	private static final Logger logger = LoggerFactory.getLogger(JmsComponentCreator.class);

	private final ConnectionFactoryProvider connectionFactoryProvider;

	private DestinationResolver destinationResolver;

	public JmsComponentCreator(ConnectionFactoryProvider connectionFactoryProvider) {
		this.connectionFactoryProvider = connectionFactoryProvider;
	}

	public JmsComponentCreator(ConnectionFactoryProvider connectionFactoryProvider, DestinationResolver destinationResolver) {
		this.connectionFactoryProvider = connectionFactoryProvider;
		this.destinationResolver = destinationResolver;
	}

	public void setDestinationResolver(DestinationResolver destinationResolver) {
		this.destinationResolver = destinationResolver;
	}

	public JmsComponent createComponent(String url, ClassLoader classLoader) {
		Properties env = new Properties();
		env.put(Context.PROVIDER_URL, url);
		env.put("connectionFactory", "EventFactory");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.pcbsys.nirvana.nSpace.NirvanaContextFactory");
		env.put("nirvana.setReadThreadDaemon", "true");
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Creating JMS component over JNDI using the following environment: %s", env));
		}
		return create(env, classLoader);
	}

	private JmsComponent create(Properties jndiEnvironment, ClassLoader classLoader) {
		ConnectionFactory connectionFactory = connectionFactoryProvider.connectionFactory();
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		JmsConfiguration conf = new JmsConfiguration(cachingConnectionFactory);
		if (destinationResolver != null) {
			conf.setDestinationResolver(destinationResolver);
		}
		conf.setListenerConnectionFactory(connectionFactory);
		return new JmsComponent(conf);
	}
}
