package org.rumusanframework.util.parser;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 * @param <T>
 */
public interface IParser<T> {
	public T parse(Object object);
}