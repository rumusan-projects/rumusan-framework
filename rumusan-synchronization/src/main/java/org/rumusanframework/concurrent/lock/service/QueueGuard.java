/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.service;

import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
public interface QueueGuard {
	public GroupLock checkIn(Class<? extends LockingProcess<GroupLock>> classCaller, KeyValueGroup keyValueGroup,
			boolean ignoreSameProcess) throws ConcurrentAccessException;

	public void checkOut(GroupLock groupLock);
}