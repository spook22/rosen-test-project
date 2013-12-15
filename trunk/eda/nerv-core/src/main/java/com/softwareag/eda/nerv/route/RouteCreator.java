package com.softwareag.eda.nerv.route;

import com.softwareag.eda.nerv.NERVException;

public interface RouteCreator {
	
	void createRoute(String from, String eventType) throws NERVException;

}
