/*
 * Copyright 2017-2017 the original author or authors.
 */

package org.rumusanframework.reflect.field;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (7 Sept 2017)
 *
 */
public class ResolverUtils {
	private ResolverUtils() {
		// hide
	}

	public static String concate(FieldResolver... resolvers) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < resolvers.length; i++) {
			if (i == 0) {
				buff.append(resolvers[i].resolve());
			} else {
				buff.append(",").append(resolvers[i].resolve());
			}
		}

		return buff.toString();
	}
}