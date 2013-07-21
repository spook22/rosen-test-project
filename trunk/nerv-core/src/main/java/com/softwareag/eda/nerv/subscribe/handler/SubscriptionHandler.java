package com.softwareag.eda.nerv.subscribe.handler;

import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public interface SubscriptionHandler {

	void subscribe(Subscription subscription) throws Exception;

	void unsubscribe(Subscription subscription) throws Exception;

}
