package org.rumusanframework.concurrent.lock.service;

import org.rumusanframework.concurrent.lock.context.ProcessContext;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.entityinvalid.GroupLockAConflict2;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (16 Jun 2018)
 */
@Lock(keyValueGroupClass = GroupLockAConflict2.class)
public class OptimisticLockingConflict2Process extends BaseOptimisticLockingProcess {

  @Override
  protected void executeInternal(ProcessContext<GroupLock> context) {
  }
}