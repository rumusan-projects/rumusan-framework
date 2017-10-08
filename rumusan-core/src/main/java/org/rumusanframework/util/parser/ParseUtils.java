package org.rumusanframework.util.parser;

import java.sql.Timestamp;
import java.util.Date;

import org.rumusanframework.util.parser.impl.ParseToFloatingChain;
import org.rumusanframework.util.parser.impl.ParseToNonFloatingChain;

/**
 * <pre>
 * Fast parsing object.
 * </pre>
 * 
 * @author Harvan Irsyadi
 * @since 1.0.0
 *
 */
public class ParseUtils {
	private static IGenericParser<Number> parseToFloatingChain = new ParseToFloatingChain(null);
	private static IGenericParser<Number> parseToNonFloatingChain = new ParseToNonFloatingChain(parseToFloatingChain);

	private ParseUtils() {
	}

	public static <T> T parse(Object obj, IParser<T> parser) {
		if (parser == null) {
			throw new IllegalArgumentException("Parser cannot be null.");
		}
		return parser.parse(obj);
	}

	public static <T> T parse(Object obj, Class<T> targetClass) {
		if (targetClass == null) {
			throw new IllegalArgumentException("Target class cannot be null.");
		}
		if (targetClass.isInstance(obj) || obj == null) {
			return targetClass.cast(obj);
		}
		if (Number.class.isAssignableFrom(targetClass)) {
			return parseToNumber(obj, targetClass);
		}
		if (Boolean.class.equals(targetClass)) {
			return parseToBoolean(obj, targetClass);
		}
		if (Date.class.isAssignableFrom(targetClass)) {
			return parseToDate(obj, targetClass);
		}
		if (Character.class.isAssignableFrom(targetClass)) {
			char[] carr = obj.toString().toCharArray();
			if (carr.length != 1) {
				throw new IllegalArgumentException("Invalid character constant");
			}

			return targetClass.cast(carr[0]);
		}
		if (String.class.isAssignableFrom(targetClass)) {
			return targetClass.cast(obj.toString());
		}

		throw new ParseException("No wrapper found for target class : " + targetClass);
	}

	@SuppressWarnings("unchecked")
	private static <T> T parseToNumber(Object obj, Class<T> targetClass) {
		return (T) parseToNonFloatingChain.parse(obj, (Class<Number>) targetClass);
	}

	private static <T> T parseToBoolean(Object obj, Class<T> targetClass) {
		String string = obj.toString();
		Boolean value;

		if ("1".equals(string)) {
			value = true;
		} else if ("0".equals(string)) {
			value = false;
		} else {
			value = Boolean.valueOf(string);
		}

		return targetClass.cast(value);
	}

	private static <T> T parseToDate(Object obj, Class<T> targetClass) {
		Date date = null;
		String string = null;

		if (obj instanceof Date) {
			date = (Date) obj;
		} else {
			string = obj.toString();
		}

		if (Timestamp.class.equals(targetClass)) {
			if (date != null) {
				return targetClass.cast(new Timestamp(date.getTime()));
			}
			return targetClass.cast(Timestamp.valueOf(string));
		} else if (java.sql.Date.class.equals(targetClass)) {
			if (date != null) {
				return targetClass.cast(new java.sql.Date(date.getTime()));
			}
			return targetClass.cast(java.sql.Date.valueOf(string));
		}

		throw new ParseException("No Date wrapper found for target class : " + targetClass);
	}
}