package org.rumusanframework.util.parser;

/**
 * 
 * @author Harvan Irsyadi
 * @since 1.0.0
 *
 */
public class ParseException extends RuntimeException {
	private static final long serialVersionUID = -7817276967158136408L;

	public ParseException() {
		super();
	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}