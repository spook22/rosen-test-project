package com.softwareag.eda.nerv.connection;

import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;

public interface NERVConnection extends Publisher, SubscriptionHandler {

	void close();

}
