package com.softwareag.eda.nerv.route;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NERVDynamicRouter {

	private static final Logger logger = LoggerFactory.getLogger(NERVDynamicRouter.class);

	public String route(String body, @Header("Channel") String channel, @Header(Exchange.SLIP_ENDPOINT) String previous) {
		String result = null;
		if (previous == null) {
			result = channel;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Routing event to: " + result);
		}
		return result;
	}

}
