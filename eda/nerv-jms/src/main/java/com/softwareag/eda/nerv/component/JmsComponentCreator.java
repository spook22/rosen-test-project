package com.softwareag.eda.nerv.component;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.jms.connection.CachingConnectionFactory;

import com.softwareag.eda.nerv.jms.ConnectionFactoryProvider;
import com.softwareag.eda.nerv.jms.DestinationResolverProvider;

public class JmsComponentCreator {

	private final ConnectionFactoryProvider connectionFactoryProvider;

	private DestinationResolverProvider destinationResolverProvider;

	public JmsComponentCreator(ConnectionFactoryProvider connectionFactoryProvider) {
		this.connectionFactoryProvider = connectionFactoryProvider;
	}

	public JmsComponentCreator(ConnectionFactoryProvider connectionFactoryProvider, DestinationResolverProvider destinationResolverProvider) {
		this.connectionFactoryProvider = connectionFactoryProvider;
		this.destinationResolverProvider = destinationResolverProvider;
	}

	public void setDestinationResolverProvider(DestinationResolverProvider destinationResolverProvider) {
		this.destinationResolverProvider = destinationResolverProvider;
	}

	public JmsComponent createComponent(String url, ClassLoader classLoader) {
		Properties env = new Properties();
		env.put(Context.PROVIDER_URL, url);
		env.put("connectionFactory", "EventFactory");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.pcbsys.nirvana.nSpace.NirvanaContextFactory");
		env.put("nirvana.setReadThreadDaemon", "true");
		return createJmsComponent(env, classLoader);
	}

	private JmsComponent createJmsComponent(Properties jndiEnvironment, ClassLoader classLoader) {
		ConnectionFactory connectionFactory = connectionFactoryProvider.connectionFactory();
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		JmsConfiguration jmsConfiguration = new JmsConfiguration(cachingConnectionFactory);
		if (destinationResolverProvider != null) {
			jmsConfiguration.setDestinationResolver(destinationResolverProvider.destinationResolver());
		}
		jmsConfiguration.setListenerConnectionFactory(connectionFactory);
		return new JmsComponent(jmsConfiguration);
	}
}
