/*
 * Copyright 2017-2017 the original author or authors.
 */

package org.rumusanframework.util.parser;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Oct 2017)
 *
 * @param <T>
 */
public interface IGenericParser<T> {
	T parse(Object obj, Class<T> targetClass);
}