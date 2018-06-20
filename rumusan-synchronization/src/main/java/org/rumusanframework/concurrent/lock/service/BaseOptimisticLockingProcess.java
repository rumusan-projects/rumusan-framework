/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.context.ProcessContext;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;
import org.rumusanframework.util.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
public abstract class BaseOptimisticLockingProcess implements LockingProcess<GroupLock> {
	private final Log logger = LogFactory.getLog(getClass());
	private QueueGuard optimisticLockingQueueGuard;
	private KeyValueGroup keyValueGroup;
	private boolean ignoreSameProcess = false;
	private boolean init = false;
	private static final Map<String, Boolean> keyValGroupMapValidated = new HashMap<>();

	@Autowired
	public void setOptimisticLockingQueueGuard(QueueGuard optimisticLockingQueueGuard) {
		this.optimisticLockingQueueGuard = optimisticLockingQueueGuard;
	}

	Log logger() {
		return logger;
	}

	protected boolean isIgnoreSameProcess() {
		return ignoreSameProcess;
	}

	protected KeyValueGroup getKeyValueGroup() {
		return keyValueGroup;
	}

	private boolean isValid() {
		return getClass().isAnnotationPresent(Lock.class);
	}

	protected abstract void executeInternal(ProcessContext<GroupLock> context);

	@Override
	public void execute(ProcessContext<GroupLock> context) throws ConcurrentAccessException {
		GroupLock object = optimisticLockingQueueGuard.checkIn(getClass(), getKeyValueGroup(), isIgnoreSameProcess());
		context.setObject(object);

		executeInternal(context);

		optimisticLockingQueueGuard.checkOut(object);
	}

	@PostConstruct
	public void init() {
		synchronized (this) {
			if (!init) {
				if (!isValid()) {
					throw new RuntimeException("Not a valid synchronize process : " + this.getClass().getName());// NOSONAR
				}

				Lock lock = getClass().getAnnotation(Lock.class);

				validateKeyValueGroup(lock);

				Class<?> keyValueGroupClass = lock.keyValueGroupClass();

				keyValueGroup = keyValueGroupClass.getAnnotation(KeyValueGroup.class);
				ignoreSameProcess = lock.ignoreSameProcess();

				logger().info("Initializing...");
				logger().info("KeyValueGroup : " + keyValueGroup);
				logger().info("IgnoreSameProcess : " + ignoreSameProcess);

				init = true;
			}
		}
	}

	private void validateKeyValueGroup(Lock lock) {
		synchronized (BaseOptimisticLockingProcess.class) {
			String packageName = lock.keyValueGroupClass().getPackage().getName();

			if (!keyValGroupMapValidated.containsKey(packageName)) {
				List<Class<?>> classList = ClassUtils.getClassByAnnotation(KeyValueGroup.class, packageName);

				validateKeyValueClass(classList);

				keyValGroupMapValidated.put(packageName, true);
			}
		}
	}

	private void validateKeyValueClass(List<Class<?>> classList) {
		Map<Long, String> keyValue = new HashMap<>();

		for (Class<?> clazz : classList) {
			KeyValueGroup keyVal = clazz.getAnnotation(KeyValueGroup.class);

			String existingValue = keyValue.get(keyVal.key());
			if (logger().isDebugEnabled()) {
				logger().debug("Validate KeyValueGroup.");
				logger().debug(String.format("ExistingValue of key(%s): %s", keyVal.key(), existingValue));
				logger().debug("key: " + keyVal.key() + ", value: " + keyVal.value());
			}

			if (existingValue != null) {
				throw new RuntimeException( // NOSONAR
						String.format("Found conflict KeyValueGroup with key:'%s', value: '%s' <> '%s'", keyVal.key(),
								keyVal.value(), existingValue));
			} else {
				keyValue.put(keyVal.key(), keyVal.value());
			}
		}
	}
}