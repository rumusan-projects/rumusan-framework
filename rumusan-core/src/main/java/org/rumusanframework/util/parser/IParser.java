/*
 * Copyright 2017-2017 the original author or authors.
 */

package org.rumusanframework.util.parser;

/**
 * 
 * @param <T>
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Oct 2017)
 * 
 */
public interface IParser<T> {
	public T parse(Object object);
}