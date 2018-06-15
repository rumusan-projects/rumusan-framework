/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.dao;

import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.entity.GroupLock;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
public interface IGroupLockDao {
	public int optimisticCheckIn(GroupLock groupLock, Class<? extends LockingProcess<GroupLock>> classCaller,
			boolean ignoreSameProces);

	public int resetLock(GroupLock groupLock);
}