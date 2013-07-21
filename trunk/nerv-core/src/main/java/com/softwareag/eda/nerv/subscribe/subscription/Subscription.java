package com.softwareag.eda.nerv.subscribe.subscription;

import com.softwareag.eda.nerv.consume.Consumer;

public interface Subscription {

	String channel();

	Consumer consumer();

}
