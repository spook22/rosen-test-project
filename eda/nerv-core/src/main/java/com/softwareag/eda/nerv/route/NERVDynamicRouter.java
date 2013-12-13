package com.softwareag.eda.nerv.route;

import org.apache.camel.DynamicRouter;
import org.apache.camel.Header;

public class NERVDynamicRouter {

	@DynamicRouter
	public String route(@Header("Type") String channel) {
		return channel;
	}

}
