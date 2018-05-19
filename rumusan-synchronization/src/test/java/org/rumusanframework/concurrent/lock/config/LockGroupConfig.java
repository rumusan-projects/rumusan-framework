package org.rumusanframework.concurrent.lock.config;

import org.rumusanframework.DefaultTestConfig;
import org.rumusanframework.concurrent.lock.BasePackageRumusanConcurrentLock;
import org.rumusanframework.concurrent.lock.entity.BasePackageRumusanConcurrentLockEntity;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@ComponentScan(basePackages = { BasePackageRumusanConcurrentLock.PACKAGE })
public class LockGroupConfig extends DefaultTestConfig {
	@Override
	protected String[] getPackageToScan() {
		return new String[] { BasePackageRumusanConcurrentLockEntity.PACKAGE };
	}
}