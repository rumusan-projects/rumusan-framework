/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.reflect.field;

import static org.junit.Assert.assertEquals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.Test;
import org.rumusanframework.reflect.field.ResolverTest.Child.Id5;
import org.rumusanframework.reflect.field.ResolverTest.Child.Id6;
import org.rumusanframework.reflect.field.ResolverTest.Parent.Id1;
import org.rumusanframework.reflect.field.ResolverTest.Parent.Id2;
import org.rumusanframework.reflect.field.ResolverTest.Parent.Id3;
import org.rumusanframework.reflect.field.ResolverTest.Parent.Id4;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (16 Jun 2018)
 */
public class ResolverTest {

  static class Parent {

    @Id1
    private String id1;
    @Id2
    private String id2;
    @Id3
    private String id3;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Id1 {

    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Id2 {

    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Id3 {

    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Id4 {

    }
  }

  static class Child extends Parent {

    @Id5
    private String id5;
    @Id6
    private String id6;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Id5 {

    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Id6 {

    }
  }

  @Test
  public void testInlineResolveFirstAttempt() {
    InLineResolver resolver = new InLineResolver(getClass(), Id1.class, Id2.class, Id3.class,
        Id4.class, Id5.class,
        Id6.class);
    String value = ResolverUtils.concate(resolver);
    assertEquals("id1,id2,id3,id5,id6", value);
  }

  @Test
  public void testInlineResolveSecondAttempt() {
    InLineResolver resolver = new InLineResolver(getClass(), Id1.class, Id2.class, Id3.class,
        Id4.class, Id5.class,
        Id6.class);
    String value = ResolverUtils.concate(resolver);
    assertEquals("id1,id2,id3,id5,id6", value);

    value = ResolverUtils.concate(resolver);
    assertEquals("id1,id2,id3,id5,id6", value);
  }

  @Test
  public void testRecursiveResolveFirstAttempt() {
    RecursiveResolver resolver = new RecursiveResolver(getClass(), Id1.class, Id2.class, Id3.class,
        Id4.class,
        Id5.class, Id6.class);
    String value = ResolverUtils.concate(resolver);
    assertEquals("id1.id2.id3.id5.id6", value);
  }

  @Test
  public void testRecursiveResolveSecondAttempt() {
    RecursiveResolver resolver = new RecursiveResolver(getClass(), Id1.class, Id2.class, Id3.class,
        Id4.class,
        Id5.class, Id6.class);
    String value = ResolverUtils.concate(resolver);
    assertEquals("id1.id2.id3.id5.id6", value);

    value = ResolverUtils.concate(resolver);
    assertEquals("id1.id2.id3.id5.id6", value);
  }

  @Test
  public void testResolveUtils() {
    RecursiveResolver resolver1 = new RecursiveResolver(getClass(), Id1.class, Id2.class, Id3.class,
        Id4.class,
        Id5.class, Id6.class);
    InLineResolver resolver2 = new InLineResolver(getClass(), Id1.class, Id2.class, Id3.class,
        Id4.class, Id5.class,
        Id6.class);

    String value = ResolverUtils.concate(resolver1, resolver2);
    assertEquals("id1.id2.id3.id5.id6,id1,id2,id3,id5,id6", value);
  }
}