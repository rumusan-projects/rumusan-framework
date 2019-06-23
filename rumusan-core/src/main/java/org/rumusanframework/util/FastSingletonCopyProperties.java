/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * For performance perspective, please ensure the instance of this class instantiate only once for
 * each bean.
 *
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (19 Feb 2018)
 */
public class FastSingletonCopyProperties {

  private Class<?> beanClass;
  private String[] excludedFieldNames;
  private Field[] fields;

  public FastSingletonCopyProperties(Class<?> beanClass) {
    this.beanClass = beanClass;
    init();
  }

  public FastSingletonCopyProperties(Class<?> beanClass, String[] excludedFieldNames) {
    this.beanClass = beanClass;
    this.excludedFieldNames = excludedFieldNames;
    init();
  }

  private void init() {
    Field[] allField = ClassUtils.getAllField(beanClass);
    List<Field> fieldList = new ArrayList<>();

    for (Field field : allField) {
      if (isValidField(field)) {
        field.setAccessible(true);
        fieldList.add(field);
      }
    }

    fields = fieldList.toArray(new Field[fieldList.size()]);
  }

  private boolean isValidField(Field field) {
    return isValidFieldModifier(field) && isValidFieldName(field.getName());
  }

  private boolean isValidFieldModifier(Field field) {
    int modifier = field.getModifiers();
    return !Modifier.isFinal(modifier);
  }

  private boolean isValidFieldName(String fieldName) {
    if (excludedFieldNames != null) {
      for (String excludedFieldName : excludedFieldNames) {
        if (excludedFieldName != null && excludedFieldName.equals(fieldName)) {
          return false;
        }
      }
    }
    return true;
  }

  public void copy(Object source, Object destination) {
    if (source != null && destination != null) {
      for (Field field : fields) {
        try {
          field.set(destination, field.get(source));
        } catch (Exception e) { // NOSONAR
        }
      }
    }
  }
}