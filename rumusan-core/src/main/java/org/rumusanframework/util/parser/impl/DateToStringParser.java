/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util.parser.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.rumusanframework.util.parser.IParser;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (28 Jan 2018)
 *
 */
public class DateToStringParser implements IParser<String> {
	private SimpleDateFormat format;

	public DateToStringParser(String pattern) {
		format = new SimpleDateFormat(pattern);
	}

	@Override
	public String parse(Object object) {
		return format.format((Date) object);
	}
}