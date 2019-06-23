package org.rumusanframework.concurrent.lock.service;

import org.rumusanframework.concurrent.lock.context.ProcessContext;
import org.rumusanframework.concurrent.lock.entity.GroupLock;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Jun 2018)
 */
public class OptimisticLockingInvalidProcess extends BaseOptimisticLockingProcess {

  @Override
  protected void executeInternal(ProcessContext<GroupLock> context) {
  }
}