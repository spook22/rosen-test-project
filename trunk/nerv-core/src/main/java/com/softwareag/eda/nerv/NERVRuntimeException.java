package com.softwareag.eda.nerv;

public class NERVRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NERVRuntimeException() {
	}

	public NERVRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NERVRuntimeException(String message) {
		super(message);
	}

	public NERVRuntimeException(Throwable cause) {
		super(cause);
	}

}
