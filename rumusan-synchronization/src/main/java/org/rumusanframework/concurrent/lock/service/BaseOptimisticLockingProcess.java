package org.rumusanframework.concurrent.lock.service;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.context.ProcessContext;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.entity.GroupLockEnum;
import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public abstract class BaseOptimisticLockingProcess implements LockingProcess<GroupLock> {
    private final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private QueueGuard queueGuardLockImpl;
    private GroupLockEnum groupLockEnum;
    private boolean ignoreSameProcess = false;
    private boolean init = false;

    Log logger() {
	return logger;
    }

    protected abstract void executeInternal(ProcessContext<GroupLock> context);

    @PostConstruct
    public void init() {
	synchronized (this) {
	    if (!init) {
		if (!isValid()) {
		    throw new RuntimeException("Not a valid synchronize process : " + this.getClass().getName());// NOSONAR
		}

		Lock synchronize = getClass().getAnnotation(Lock.class);
		groupLockEnum = synchronize.groupEnum();
		ignoreSameProcess = synchronize.ignoreSameProcess();

		if (logger().isDebugEnabled()) {
		    logger().debug("GroupLockEnum : " + groupLockEnum);
		    logger().debug("IgnoreSameProcess : " + ignoreSameProcess);
		}
	    }
	}
    }

    protected boolean isIgnoreSameProcess() {
	return ignoreSameProcess;
    }

    protected GroupLockEnum getGroupLockEnum() {
	return groupLockEnum;
    }

    private boolean isValid() {
	return getClass().isAnnotationPresent(Lock.class);
    }

    @Override
    public void execute(ProcessContext<GroupLock> context) throws ConcurrentAccessException {
	GroupLock object = queueGuardLockImpl.checkIn(getClass(), getGroupLockEnum(), isIgnoreSameProcess());
	context.setObject(object);

	executeInternal(context);

	queueGuardLockImpl.checkOut(object);
    }
}