package org.rumusanframework.concurrent.lock.service;

import org.rumusanframework.concurrent.lock.context.ProcessContext;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.entityinvalid.GroupLockAConflict1;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (16 Jun 2018)
 *
 */
@Lock(keyValueGroupClass = GroupLockAConflict1.class)
public class OptimisticLockingConflict1Process extends BaseTestLockingProcess {
	@Override
	protected void executeInternal(ProcessContext<GroupLock> context) {
	}

	@Override
	protected String getKayValueGroupPackage() {
		return GroupLockAConflict1.class.getPackage().getName();
	}
}