package com.softwareag.eda.nerv.jms;

import org.apache.camel.component.jms.JmsComponent;

import com.softwareag.eda.nerv.NERV;
import com.softwareag.eda.nerv.component.ComponentNameProvider;
import com.softwareag.eda.nerv.component.DefaultComponentNameProvider;
import com.softwareag.eda.nerv.jms.intercept.JmsSupportInterceptor;
import com.softwareag.eda.nerv.jms.route.JmsRouteCreator;
import com.softwareag.eda.nerv.jms.support.UniversalMessagingSupport;

public class JmsSupport {

	protected final JmsSupportInterceptor interceptor;

	private final JmsComponentCreator componentCreator;

	public JmsSupport() {
		super();
		String jmsComponentName = "nervDefaultJms";
		String providerUrl = "nsp://localhost:9000";
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
