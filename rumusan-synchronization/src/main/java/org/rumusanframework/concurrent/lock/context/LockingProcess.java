/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.context;

import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
public interface LockingProcess<T> {
	public void execute(ProcessContext<T> context) throws ConcurrentAccessException;
}