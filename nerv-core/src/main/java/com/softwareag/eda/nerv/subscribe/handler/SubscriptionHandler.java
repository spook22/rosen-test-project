package com.softwareag.eda.nerv.subscribe.handler;

import com.softwareag.eda.nerv.NERVRuntimeException;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public interface SubscriptionHandler {

	void subscribe(Subscription subscription) throws NERVRuntimeException;

	void unsubscribe(Subscription subscription) throws NERVRuntimeException;

}
