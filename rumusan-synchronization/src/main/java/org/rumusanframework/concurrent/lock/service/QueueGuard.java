package org.rumusanframework.concurrent.lock.service;

import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.entity.GroupLockEnum;
import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public interface QueueGuard {
    public GroupLock checkIn(Class<? extends LockingProcess<GroupLock>> classCaller, GroupLockEnum groupLockEnum,
	    boolean ignoreSameProcess) throws ConcurrentAccessException;

    public void checkOut(GroupLock groupLock);
}