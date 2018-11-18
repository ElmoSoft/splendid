package net.elmosoft.splendid.driver.exceptions;

public class IncorrectPageException extends RuntimeException {

	private static final long serialVersionUID = -1207784400216235879L;

	public IncorrectPageException(String message) {
		super(message);
	}

	public IncorrectPageException(String actual, String expected) {
		super("Incorrect page is opened: " + actual + ". Expected - " + expected);
	}
}
