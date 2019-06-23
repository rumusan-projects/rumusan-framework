/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Feb 2018)
 */
public class StringUtils {

  private StringUtils() {
    // hide
  }

  public static boolean isTrimEmpty(Object str) {
    return (str == null || str.toString().trim().length() == 0);
  }
}