package org.rumusanframework.concurrent.lock.service;

import org.rumusanframework.concurrent.lock.context.ProcessContext;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.entity.GroupLockEnum;
import org.rumusanframework.concurrent.lock.service.BaseOptimisticLockingProcess;
import org.rumusanframework.concurrent.lock.service.Lock;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@Service
@Lock(groupEnum = GroupLockEnum.A, ignoreSameProcess = true)
public class OptimisticLockingSameProcess extends BaseOptimisticLockingProcess {
	@Override
	protected void executeInternal(ProcessContext<GroupLock> context) {
	}
}