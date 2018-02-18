package org.rumusanframework.validation.constraintvalidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rumusanframework.validation.constraint.StringDate;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public class StringDateValidator implements ConstraintValidator<StringDate, String> {
    private static final Log LOGGER = LogFactory.getLog(StringDateValidator.class);
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

    Log logger() {
	return LOGGER;
    }
}