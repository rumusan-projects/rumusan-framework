/*
 * Copyright 2017-2017 the original author or authors.
 */

package org.rumusanframework.util.parser;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Oct 2017)
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