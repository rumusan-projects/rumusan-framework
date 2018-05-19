package org.rumusanframework.concurrent.lock.dao;

import java.util.List;

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
	public List<GroupLock> findAll();

	public GroupLock findGroup(Long groupId, Class<?> processClass, boolean ignoreSameProcess);

	public int optimisticCheckIn(GroupLock groupLock, Class<? extends LockingProcess<GroupLock>> classCaller,
			boolean ignoreSameProces);

	public int resetLock(GroupLock groupLock);
}