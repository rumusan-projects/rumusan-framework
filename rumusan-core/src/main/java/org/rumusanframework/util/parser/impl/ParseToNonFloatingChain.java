/*
 * Copyright 2017-2017 the original author or authors.
 */

package org.rumusanframework.util.parser.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.rumusanframework.util.parser.IGenericParser;
import org.rumusanframework.util.parser.ParseException;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Oct 2017)
 *
 */
public class ParseToNonFloatingChain implements IGenericParser<Number> {
	private IGenericParser<Number> chain;

	public ParseToNonFloatingChain(IGenericParser<Number> chain) {
		this.chain = chain;
	}

	@Override
	public Number parse(Object obj, Class<Number> targetClass) {
		Number number = null;
		String text = null;

		if (obj instanceof Number) {
			number = (Number) obj;
		} else if (obj instanceof String) {
			text = (String) obj;
		} else {
			text = obj.toString();
		}

		if (Long.class.equals(targetClass)) {
			return parseToLong(number, text, targetClass);
		} else if (Integer.class.equals(targetClass)) {
			return parseToInteger(number, text, targetClass);
		} else if (Short.class.equals(targetClass)) {
			return parseToShort(number, text, targetClass);
		} else if (Byte.class.equals(targetClass)) {
			return parseToByte(number, text, targetClass);
		} else if (BigInteger.class.equals(targetClass)) {
			return parseToBigInteger(number, text, targetClass);
		} else if (chain != null) {
			return chain.parse(obj, targetClass);
		}

		throw new ParseException("No wrapper found for target class : " + targetClass);
	}

	private <T> T parseToLong(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			return targetClass.cast(number.longValue());
		}
		return targetClass.cast(Long.valueOf(text));
	}

	private <T> T parseToInteger(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			return targetClass.cast(number.intValue());
		}
		return targetClass.cast(Integer.valueOf(text));
	}

	private <T> T parseToShort(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			return targetClass.cast(number.shortValue());
		}
		return targetClass.cast(Short.valueOf(text));
	}

	private <T> T parseToByte(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			return targetClass.cast(number.byteValue());
		}
		return targetClass.cast(Byte.valueOf(text));
	}

	private <T> T parseToBigInteger(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			// keep precision
			if (number instanceof BigDecimal) {
				return targetClass.cast(((BigDecimal) number).toBigInteger());
			}
			return targetClass.cast(BigInteger.valueOf(number.longValue()));
		}
		return targetClass.cast(BigInteger.valueOf(Long.valueOf(text)));
	}
}