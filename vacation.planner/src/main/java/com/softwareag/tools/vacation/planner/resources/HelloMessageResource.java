package com.softwareag.tools.vacation.planner.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class HelloMessageResource {

	@Inject
	private HelloMessage helloMessage;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public HelloMessage getMessage() {
		return helloMessage;
	}

}
