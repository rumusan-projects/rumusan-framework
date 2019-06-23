/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Jun 2018)
 */
public class FastSingletonCopyPropertiesTest {

  private TestParent obj;

  @Before
  public void before() {
    obj = new TestParent();
    obj.setFieldParent2(123L);
    obj.setFieldParent1("value");
  }

  /**
   * Copy All.
   */
  @Test
  public void testCopy() {
    FastSingletonCopyProperties copyProperties = new FastSingletonCopyProperties(obj.getClass());
    // cover 2 condition
    copyProperties = new FastSingletonCopyProperties(obj.getClass());
    TestParent copy = new TestParent();

    assertNull(copy.getFieldParent1());
    assertNull(copy.getFieldParent2());
    copyProperties.copy(obj, copy);

    assertNotNull(copy.getFieldParent1());
    assertNotNull(copy.getFieldParent2());
    assertEquals(obj.getFieldParent1(), copy.getFieldParent1());
    assertEquals(obj.getFieldParent2(), copy.getFieldParent2());

    copyProperties.copy(null, copy);
    copyProperties.copy(obj, null);
  }

  @Test
  public void testCopySelected() {
    String[] excludes = {"fieldParent1", "wrongField", null};
    FastSingletonCopyProperties copyProperties = new FastSingletonCopyProperties(obj.getClass(),
        excludes);

    TestParent copy = new TestParent();

    assertNull(copy.getFieldParent1());
    assertNull(copy.getFieldParent2());
    copyProperties.copy(obj, copy);

    assertNull(copy.getFieldParent1());
    assertNotNull(copy.getFieldParent2());
    assertEquals(obj.getFieldParent2(), copy.getFieldParent2());
  }
}