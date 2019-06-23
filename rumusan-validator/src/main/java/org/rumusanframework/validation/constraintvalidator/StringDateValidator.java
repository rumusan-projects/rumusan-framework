/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.validation.constraintvalidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.rumusanframework.validation.constraint.StringDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (18 Feb 2018)
 */
public class StringDateValidator implements ConstraintValidator<StringDate, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(StringDateValidator.class);
  private SimpleDateFormat format;

  @Override
  public void initialize(StringDate annotation) {
    if (logger().isDebugEnabled()) {
      logger().debug("Initialize...");
      logger().debug("dateFormat : " + annotation.dateFormat());
    }

    format = new SimpleDateFormat(annotation.dateFormat());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (logger().isDebugEnabled()) {
      logger().debug("Validating : " + value);
    }

    try {
      format.parse(value);

      return true;
    } catch (ParseException e) {
      if (logger().isErrorEnabled()) {
        logger().error("Error when parsing field.", e);
      }

      return false;
    }
  }

  Logger logger() {
    return LOGGER;
  }
}