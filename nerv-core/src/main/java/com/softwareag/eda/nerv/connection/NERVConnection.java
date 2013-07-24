package com.softwareag.eda.nerv.connection;

import com.softwareag.eda.nerv.publish.Publisher;
import com.softwareag.eda.nerv.subscribe.handler.SubscriptionHandler;

public interface NERVConnection extends Publisher, SubscriptionHandler {

	String PROP_CHANNEL_TYPE = "nerv.channel.type";

	String PROP_CHANNEL_TYPE_DIRECT = "direct";

	String PROP_CHANNEL_TYPE_VM = "vm";

	void close();

}
