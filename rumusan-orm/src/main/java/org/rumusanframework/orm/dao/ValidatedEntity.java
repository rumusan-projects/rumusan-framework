/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.dao;

import java.io.Serializable;

import javax.validation.Validator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The concrete implementation will contain Bean Validation API annotation like
 * {@link NotNull}, {@link NotEmpty} to be validated using
 * {@link Validator}.<br/>
 * Example of code:<br/>
 * 
 * <pre>
 * class ConcreteValidatedEntity implements ValidatedEntity {
 * 	{@literal @}NotNull(message = "Object cannot be null.")
 * 	private {@link Serializable} object;
 * 
 * 	ConcreteValidatedEntity({@link Serializable} object) {
 * 		this.object = object;
 * 	}
 * }
 * </pre>
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
public interface ValidatedEntity {
}