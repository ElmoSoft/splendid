package net.elmosoft.splendid.driver.exceptions;

public class CommonTestRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 3670097603940832796L;

	public CommonTestRuntimeException(String message) {
		super(message);
	}

	public CommonTestRuntimeException() {
		super();
	}

	public CommonTestRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
