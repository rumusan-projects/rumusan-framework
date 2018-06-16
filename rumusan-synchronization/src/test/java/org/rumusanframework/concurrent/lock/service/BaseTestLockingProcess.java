package org.rumusanframework.concurrent.lock.service;

import org.rumusanframework.concurrent.lock.entity.GroupLockA;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (16 Jun 2018)
 *
 */
public abstract class BaseTestLockingProcess extends BaseOptimisticLockingProcess {
	@Override
	protected String getKayValueGroupPackage() {
		return GroupLockA.class.getPackage().getName();
	}
}