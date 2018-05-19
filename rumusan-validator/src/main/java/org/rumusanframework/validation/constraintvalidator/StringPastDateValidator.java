package org.rumusanframework.validation.constraintvalidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rumusanframework.validation.constraint.StringPastDate;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (19 Feb 2018)
 *
 */
public class StringPastDateValidator implements ConstraintValidator<StringPastDate, String> {
	private static final Log LOGGER = LogFactory.getLog(StringPastDateValidator.class);
	private SimpleDateFormat format;

	@Override
	public void initialize(StringPastDate annotation) {
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
			Date date = format.parse(value);

			return date.compareTo(new Date()) < 0;
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