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
public class ParseToFloatingChain implements IGenericParser<Number> {
	private IGenericParser<Number> chain;

	public ParseToFloatingChain(IGenericParser<Number> chain) {
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

		if (Double.class.equals(targetClass)) {
			return parseToDouble(number, text, targetClass);
		} else if (Float.class.equals(targetClass)) {
			return parseToFloat(number, text, targetClass);
		} else if (BigDecimal.class.equals(targetClass)) {
			return parseToBigDecimal(number, text, targetClass);
		} else if (chain != null) {
			return chain.parse(obj, targetClass);
		}

		throw new ParseException("No wrapper found for target class : " + targetClass);
	}

	private <T> T parseToDouble(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			return targetClass.cast(number.doubleValue());
		}
		return targetClass.cast(Double.valueOf(text));
	}

	private <T> T parseToFloat(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			return targetClass.cast(number.floatValue());
		}
		return targetClass.cast(Float.valueOf(text));
	}

	private <T> T parseToBigDecimal(Number number, String text, Class<T> targetClass) {
		if (number != null) {
			if (number instanceof Double) {
				return targetClass.cast(new BigDecimal((Double) number));
			}
			if (number instanceof Float) {
				return targetClass.cast(new BigDecimal((Float) number));
			}
			// keep precision
			if (number instanceof BigInteger) {
				return targetClass.cast(new BigDecimal((BigInteger) number));
			}
			return targetClass.cast(BigDecimal.valueOf(number.longValue()));
		}
		return targetClass.cast(new BigDecimal(text));
	}
}