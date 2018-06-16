/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.DbTimestampType;
import org.hibernate.type.VersionType;
import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.dao.IGroupLockDao;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;
import org.rumusanframework.orm.dao.DaoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@Service
public class OptimisticLockingQueueGuard implements QueueGuard {
	private final Log logger = LogFactory.getLog(getClass());
	private IGroupLockDao groupLockDao;
	private String hostName;
	private ISystemDate systemDate;

	@Autowired
	public void setSyncDao(IGroupLockDao groupLockDao) {
		this.groupLockDao = groupLockDao;
	}

	Log logger() {
		return logger;
	}

	@PostConstruct
	public void init() throws UnknownHostException {
		initHostName();
		systemDate = new DbSystemDate();
	}

	private void initHostName() throws UnknownHostException {
		hostName = InetAddress.getLocalHost().getHostName();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public GroupLock checkIn(Class<? extends LockingProcess<GroupLock>> classCaller, KeyValueGroup keyValueGroup,
			boolean ignoreSameProcess) throws ConcurrentAccessException {
		GroupLock newGroupLock = getPreparedObject(classCaller, keyValueGroup);
		validateOptimisticUpdate(classCaller, newGroupLock, ignoreSameProcess);

		if (logger().isDebugEnabled()) {
			logger().debug("CheckIn sucessfully. " + newGroupLock);
		}

		return newGroupLock;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void checkOut(GroupLock groupLock) {
		int result = groupLockDao.resetLock(groupLock);

		if (logger().isDebugEnabled()) {
			if (result > 0) {
				logger().debug("CheckOut sucessfully : " + groupLock);
			} else {
				logger().error("CheckOut failed : " + groupLock);
			}
		}
	}

	private void throwConcurrentProcess(Class<? extends LockingProcess<GroupLock>> classCaller, GroupLock groupLock,
			boolean isValid) throws ConcurrentAccessException {
		if (!isValid) {
			throw new ConcurrentAccessException("Found concurrent process in the same group.", classCaller,
					groupLock.getGroupName(), groupLock.getMachineName());
		}
	}

	private GroupLock getPreparedObject(Class<? extends LockingProcess<GroupLock>> classCaller,
			KeyValueGroup keyValueGroup) {
		GroupLock groupLock = new GroupLock();

		groupLock.setGroupId(keyValueGroup.key());
		groupLock.setGroupName(keyValueGroup.value());
		groupLock.setProcessName(classCaller.getName());
		groupLock.setProcessId(UUID.randomUUID().toString());
		groupLock.setMachineName(hostName);

		Session session = ((DaoTemplate<?>) groupLockDao).getSession();
		groupLock.setLastUpdateTime(systemDate.getSystemDate(session));

		groupLock.setLastUpdateProcessName(classCaller.getName());
		groupLock.setLastUpdateProcessId(groupLock.getProcessId());

		return groupLock;
	}

	private void validateOptimisticUpdate(Class<? extends LockingProcess<GroupLock>> classCaller, GroupLock groupLock,
			boolean ignoreSameProcess) throws ConcurrentAccessException {
		int result = groupLockDao.optimisticCheckIn(groupLock, classCaller, ignoreSameProcess);
		boolean valid = result > 0;

		throwConcurrentProcess(classCaller, groupLock, valid);
	}
}

interface ISystemDate {
	Date getSystemDate(Session session);
}

/**
 * Strategy to produce <code>system date</code> using DB system date.
 * 
 * @author Harvan Irsyadi
 *
 */
class DbSystemDate implements ISystemDate {
	private VersionType<Date> dbTimeStamp = new DbTimestampType();

	@Override
	public Date getSystemDate(Session session) {
		return dbTimeStamp.seed((SessionImplementor) session);
	}
}