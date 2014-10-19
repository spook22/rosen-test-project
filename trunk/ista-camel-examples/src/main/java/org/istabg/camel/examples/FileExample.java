package org.istabg.camel.examples;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileExample {
	
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("file:inbox/orders?initialDelay=0").to("file:outbox/orders");
			}
		});
		context.start();
		
		Thread.sleep(2000);
	}

}
