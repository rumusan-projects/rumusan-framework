package org.rumusanframework.util;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public class StringUtils {
	private StringUtils() {
		// hide
	}

	public static boolean isTrimEmpty(Object str) {
		return (str == null || str.toString().trim().length() == 0);
	}
}