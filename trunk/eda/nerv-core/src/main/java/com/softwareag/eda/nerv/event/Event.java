package com.softwareag.eda.nerv.event;

import java.util.Collections;
import java.util.Map;

import org.apache.camel.util.CaseInsensitiveMap;

import com.softwareag.eda.nerv.NERVException;

public class Event {

	private final Map<String, Object> headers = new CaseInsensitiveMap();

	private final Object body;

	public Event(String type, Object body) {
		this.headers.put(Header.TYPE.getName(), type);
		this.body = body;
	}

	public Event(Map<String, Object> headers, Object body) throws NERVException {
		if (headers == null) {
			throw new NERVException("Headers cannot be null. The type header is mandatory.");
		}
		if (headers.get(Header.TYPE.getName()) == null) {
			throw new NERVException("Event type is missing.");
		}
		if (body == null) {
			throw new NERVException("Event body is missing.");
		}
		this.headers.putAll(headers);
		this.body = body;
	}

	public Map<String, Object> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}

	public Object getBody() {
		return body;
	}

	public String getType() {
		return getHeaderAsStr(Header.TYPE);
	}

	public Object getHeader(Header header) {
		return getHeader(header.getName());
	}

	public Object getHeader(String name) {
		return headers.get(name);
	}

	public String getHeaderAsStr(Header header) {
		return (String) getHeader(header);
	}

	protected void setHeader(Header header, String value) {
		headers.put(header.getName(), value);
	}

	@Override
	public String toString() {
		return "Event [headers=" + headers + ", body=" + body + "]";
	}

}
