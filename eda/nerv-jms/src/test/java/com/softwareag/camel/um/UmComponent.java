package com.softwareag.camel.um;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import com.pcbsys.nirvana.client.nSession;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.client.nSessionFactory;

public class UmComponent extends UriEndpointComponent {

	private String rname;

	public UmComponent() {
		super(UmEndpoint.class);
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		UmEndpoint endpoint = new UmEndpoint(uri, this);
		setProperties(endpoint, parameters);
		return endpoint;
	}

	protected nSession createSession() {
		try {
			String[] RNAME = { rname };
			nSessionAttributes sessionAttributes = new nSessionAttributes(RNAME);
			nSession session = nSessionFactory.create(sessionAttributes);
			session.init();
			return session;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
