package org.rumusanframework.concurrent.lock.context;

import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public interface LockingProcess<T> {
    public void execute(ProcessContext<T> context) throws ConcurrentAccessException;
}