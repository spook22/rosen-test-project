package com.softwareag.eda.nerv.connection;

import java.util.Map;

import com.softwareag.eda.nerv.NERVException;
import com.softwareag.eda.nerv.event.Event;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;

public class VMConnection implements NERVConnection {

	@Override
	public void publish(String type, Object body) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publish(Map<String, Object> headers, Object body) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publish(Event event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribe(Subscription subscription) throws NERVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribe(Subscription subscription) throws NERVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
