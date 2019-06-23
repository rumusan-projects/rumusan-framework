package org.rumusanframework.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TestParent {

  @SelectedField
  @FieldParent1
  private String fieldParent1;
  @SelectedField
  private Long fieldParent2;
  public static final String CONSTANT = "";
  public static String CONSTANT2 = "";
  private final Long finalField = 10L;

  public String getFieldParent1() {
    return fieldParent1;
  }

  public void setFieldParent1(String fieldParent1) {
    this.fieldParent1 = fieldParent1;
  }

  public Long getFieldParent2() {
    return fieldParent2;
  }

  public void setFieldParent2(Long fieldParent2) {
    this.fieldParent2 = fieldParent2;
  }

  public Long getFinalField() {
    return finalField;
  }

  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface FieldParent1 {

  }

  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface FieldParent2 {

  }

  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface SelectedField {

  }
}