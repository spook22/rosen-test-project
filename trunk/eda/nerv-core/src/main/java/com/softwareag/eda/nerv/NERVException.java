package com.softwareag.eda.nerv;

public class NERVException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NERVException() {
	}

	public NERVException(String message, Throwable cause) {
		super(message, cause);
	}

	public NERVException(String message) {
		super(message);
	}

	public NERVException(Throwable cause) {
		super(cause);
	}

}
