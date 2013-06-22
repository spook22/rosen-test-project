package com.softwareag.tools.vacation.planner.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HelloMessage {
	
	@XmlElement
	private String message = "Hello, World.";
	
	public HelloMessage() {
		super();
		System.out.println("Created HelloMessage class.");
	}

	@Override
	public String toString() {
		return message;
	}

}
