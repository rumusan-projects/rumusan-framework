package org.rumusanframework.util.parser;

/**
 * 
 * @author Harvan Irsyadi
 * @since 1.0.0
 *
 * @param <T>
 */
public interface IGenericParser<T> {
	T parse(Object obj, Class<T> targetClass);
}