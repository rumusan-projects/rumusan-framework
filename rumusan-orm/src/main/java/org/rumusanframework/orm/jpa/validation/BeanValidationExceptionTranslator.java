/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.jpa.validation;

import javax.validation.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (12 Mar 2018)
 */
public class BeanValidationExceptionTranslator implements PersistenceExceptionTranslator {

  @Override
  public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
    if (ex instanceof ValidationException) {
      throw ex;
    }
    return null;
  }
}