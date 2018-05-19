package org.rumusanframework.util.parser;

import java.sql.Timestamp;
import java.util.Date;

import org.rumusanframework.util.parser.impl.ParseToFloatingChain;
import org.rumusanframework.util.parser.impl.ParseToNonFloatingChain;

/**
 * <pre>
 * Fast parsing object. This class is designed to be preferred for performance rather than design patterns.
 * </pre>
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class ParserUtils {
    private static IGenericParser<Number> parseToFloatingChain = new ParseToFloatingChain(null);
    private static IGenericParser<Number> parseToNonFloatingChain = new ParseToNonFloatingChain(parseToFloatingChain);

    private ParserUtils() {
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
	Boolean value;

	if (Number.class.isInstance(obj)) {
	    int intValue = ParserUtils.parse(obj, Integer.class);

	    value = isTrue(intValue);
	} else {
	    String string = obj.toString();

	    if ("0".equals(string)) {
		value = false;
	    } else if ("1".equals(string)) {
		value = true;
	    } else if ("true".equalsIgnoreCase(string)) {
		value = true;
	    } else if ("false".equalsIgnoreCase(string)) {
		value = false;
	    } else {
		value = isTrueStringToInteger(string);
	    }
	}

	return targetClass.cast(value);
    }

    private static boolean isTrue(int intValue) {
	return intValue > 0;
    }

    private static boolean isTrueStringToInteger(String string) {
	int intValue = 0;

	try {
	    intValue = ParserUtils.parse(string, Integer.class);
	} catch (Exception e) {// NOSONAR
	}

	return isTrue(intValue);
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

    public static <T> T parse(Object obj, T defaultVal, Class<T> targetClass) {
	T returnVal = parse(obj, targetClass);

	return returnVal != null ? returnVal : defaultVal;
    }
}