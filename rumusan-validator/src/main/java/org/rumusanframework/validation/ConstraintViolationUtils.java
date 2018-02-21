package org.rumusanframework.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public class ConstraintViolationUtils {
    private ConstraintViolationUtils() {
    }

    /**
     * Will throw ConstraintViolationException if set not empty.
     * 
     * @param constraintViolations
     */
    public static <T> void performChecking(Set<ConstraintViolation<T>> constraintViolations) {
	if (!constraintViolations.isEmpty()) {
	    throw new ConstraintViolationException(constraintViolations);
	}
    }
}