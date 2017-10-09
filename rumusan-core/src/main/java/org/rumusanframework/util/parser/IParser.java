package org.rumusanframework.util.parser;

/**
 * 
 * @author Harvan Irsyadi
 * @since 1.0.0
 *
 * @param <T>
 */
public interface IParser<T> {
	public T parse(Object object);
}