/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Jun 2018)
 */
public class StringUtilsTest {

  @Test
  public void testIsTrimEmpty() {
    assertTrue(StringUtils.isTrimEmpty(""));
    assertTrue(StringUtils.isTrimEmpty(" "));
    assertTrue(StringUtils.isTrimEmpty(null));
    assertFalse(StringUtils.isTrimEmpty("1"));
  }
}