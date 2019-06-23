/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.validation.constraintvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.rumusanframework.validation.constraint.StringTrimNotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (18 Feb 2018)
 */
public class StringTrimValidator implements ConstraintValidator<StringTrimNotEmpty, CharSequence> {

  private static final Logger LOGGER = LoggerFactory.getLogger(StringTrimValidator.class);

  @Override
  public boolean isValid(CharSequence charSequence,
      ConstraintValidatorContext constraintValidatorContext) {
    if (logger().isDebugEnabled()) {
      logger().debug("Validating : " + charSequence);
    }

    if (charSequence == null) {
      return false;
    }
    return charSequence.toString().trim().length() > 0;
  }

  Logger logger() {
    return LOGGER;
  }
}