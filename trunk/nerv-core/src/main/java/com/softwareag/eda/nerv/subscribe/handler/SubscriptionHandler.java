package com.softwareag.eda.nerv.subscribe.handler;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public interface SubscriptionHandler {

	void subscribe(Subscription subscription) throws NERVException;

	void unsubscribe(Subscription subscription) throws NERVException;

}
