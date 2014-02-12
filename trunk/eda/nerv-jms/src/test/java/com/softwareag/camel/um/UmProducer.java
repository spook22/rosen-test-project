package com.softwareag.camel.um;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nConsumeEvent;
import com.pcbsys.nirvana.client.nEventProperties;

public class UmProducer extends DefaultProducer {

	public UmProducer(UmEndpoint endpoint) {
		super(endpoint);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		Object body = in.getBody();
		Map<String, Object> headers = in.getHeaders();

		nEventProperties properties = new nEventProperties();
		for (Entry<String, Object> entry : headers.entrySet()) {
			properties.put(entry.getKey(), entry.getValue().toString());
		}
		nConsumeEvent event = new nConsumeEvent("some-tag", properties, body.toString().getBytes());

		nChannel channel = getEndpoint().getChannel();
		channel.publish(event);
	}

	@Override
	public UmEndpoint getEndpoint() {
		return (UmEndpoint) super.getEndpoint();
	}
}
