package com.softwareag.eda.nerv.jms;

import org.apache.camel.component.jms.JmsComponent;

import com.softwareag.eda.nerv.NERV;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;
import com.softwareag.eda.nerv.jms.intercept.JmsSupportInterceptor;
import com.softwareag.eda.nerv.jms.route.JmsRouteCreator;
import com.softwareag.eda.nerv.jms.support.UniversalMessagingSupport;
import com.softwareag.eda.nerv.properties.NERVPropertiesProvider;

public class JmsSupport {

	protected final JmsSupportInterceptor interceptor;

	private final JmsComponentCreator componentCreator;

	public JmsSupport() {
		super();
		String jmsComponentName = NERVPropertiesProvider.instance().getProperty(
				NERVPropertiesProvider.DEFAULT_JMS_COMPONENT_NAME);
		String providerUrl = NERVPropertiesProvider.instance().getProperty(
				NERVPropertiesProvider.DEFAULT_JMS_PROVIDER_URL);
		NERV nerv = NERV.instance();

		ComponentNameProvider componentNameProvider = new DefaultComponentNameProvider(jmsComponentName);
		JmsRouteCreator routeCreator = new JmsRouteCreator(nerv.getContextProvider(), componentNameProvider);
		interceptor = new JmsSupportInterceptor(routeCreator);

		UniversalMessagingSupport umSupport = new UniversalMessagingSupport(providerUrl);
		componentCreator = new JmsComponentCreator(umSupport, umSupport);
		JmsComponent jmsComponent = componentCreator.createComponent(providerUrl, null);
		nerv.getContextProvider().context().addComponent(componentNameProvider.componentName(), jmsComponent);
	}

}
