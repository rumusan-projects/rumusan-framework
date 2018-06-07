/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.validation.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.rumusanframework.validation.constraint.StringPastDate.List;
import org.rumusanframework.validation.constraintvalidator.StringPastDateValidator;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (19 Feb 2018)
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = StringPastDateValidator.class)
public @interface StringPastDate {
	String message() default ConstraintViolationMessage.DATE_NOT_PAST;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String dateFormat() default "dd-MM-yyyy HH:mm:ss:SSSSSSS";

	/**
	 * Defines several {@link StringPastDate} annotations on the same element.
	 *
	 * @see StringPastDate
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		StringPastDate[] value();
	}
}