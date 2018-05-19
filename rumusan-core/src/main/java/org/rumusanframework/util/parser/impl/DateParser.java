package org.rumusanframework.util.parser.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.rumusanframework.util.parser.IParser;
import org.rumusanframework.util.parser.ParseException;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class DateParser implements IParser<Date> {
	private SimpleDateFormat simpleDateFormat;

	@SuppressWarnings("unused")
	private DateParser() {
		// hidden
	}

	public DateParser(String pattern) {
		simpleDateFormat = new SimpleDateFormat(pattern);
	}

	@Override
	public Date parse(Object object) {
		try {
			return simpleDateFormat.parse(object.toString());
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}
}