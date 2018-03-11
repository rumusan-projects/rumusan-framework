package org.rumusanframework.concurrent.lock.context;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public class ProcessContext<T> {
    private T object;

    public void setObject(T object) {
	this.object = object;
    }

    public T getObject() {
	return object;
    }
}