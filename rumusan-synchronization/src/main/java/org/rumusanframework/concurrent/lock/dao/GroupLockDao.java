/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.hibernate.query.Query;
import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.entity.GroupLock_;
import org.rumusanframework.orm.dao.DaoTemplate;
import org.rumusanframework.orm.dao.ValidatedEntity;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@Repository
public class GroupLockDao extends DaoTemplate<GroupLock> implements IGroupLockDao {
	private static final String KEY_1 = "key1";
	private static final String KEY_2 = "key2";
	private EntityManager entityManager;

	@PersistenceContext(unitName = "entityManagerFactory")
	@Override
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	private static final String UPDATE_SAME_PROCESS = "" + //
			"update GroupLock set processName = :processName" + //
			", machineName = :machineName, processId = :processId, lastUpdateTime = :lastUpdateTime" + //
			", lastUpdateProcessName = :lastUpdateProcessName, lastUpdateProcessId = :lastUpdateProcessId " + //
			"where groupId = :" + KEY_1 + " and (processName is null or processName = :" + KEY_2 + ")";
	private static final String UPDATE_UNIQUE_PROCESS = "" + //
			"update GroupLock set processName = :processName" + //
			", machineName = :machineName, processId = :processId, lastUpdateTime = :lastUpdateTime" + //
			", lastUpdateProcessName = :lastUpdateProcessName, lastUpdateProcessId = :lastUpdateProcessId " + //
			"where groupId = :" + KEY_1 + " and (processId is null or processId = :" + KEY_2 + ")";

	@Override
	public int optimisticCheckIn(GroupLock groupLock, Class<? extends LockingProcess<GroupLock>> classCaller,
			boolean ignoreSameProces) {
		validateContext(groupLock);

		Query<?> query = null;

		if (ignoreSameProces) {
			query = getSession().createQuery(UPDATE_SAME_PROCESS);

			setQueryParameter(query, groupLock);

			query.setParameter(KEY_1, groupLock.getGroupId());
			query.setParameter(KEY_2, groupLock.getProcessName());

			return query.executeUpdate();
		} else {
			query = getSession().createQuery(UPDATE_UNIQUE_PROCESS);

			setQueryParameter(query, groupLock);

			query.setParameter(KEY_1, groupLock.getGroupId());
			query.setParameter(KEY_2, groupLock.getProcessId());

			return query.executeUpdate();
		}
	}

	private void setQueryParameter(Query<?> query, GroupLock groupLock) {
		query.setParameter(GroupLock_.PROCESS_NAME, groupLock.getProcessName());
		query.setParameter(GroupLock_.MACHINE_NAME, groupLock.getMachineName());
		query.setParameter(GroupLock_.PROCESS_ID, groupLock.getProcessId());
		query.setParameter(GroupLock_.LAST_UPDATE_TIME, groupLock.getLastUpdateTime());
		query.setParameter(GroupLock_.LAST_UPDATE_PROCESS_NAME, groupLock.getLastUpdateProcessName());
		query.setParameter(GroupLock_.LAST_UPDATE_PROCESS_ID, groupLock.getLastUpdateProcessId());

	}

	private static final String UPDATE_RESET_LOCK = "" + //
			"update GroupLock set processName = :processName, machineName = :machineName" + //
			", processId = :processId where processId = :" + KEY_1;

	@Override
	public int resetLock(GroupLock groupLock) {
		validateContext(groupLock);

		Query<?> query = getSession().createQuery(UPDATE_RESET_LOCK);

		query.setParameter(GroupLock_.PROCESS_NAME, null);
		query.setParameter(GroupLock_.MACHINE_NAME, null);
		query.setParameter(GroupLock_.PROCESS_ID, null);
		query.setParameter(KEY_1, groupLock.getProcessId());

		return query.executeUpdate();
	}

	class GroupLockValidatedEntity implements ValidatedEntity {
		@NotNull(message = "Group lock instance cannot be null.")
		private Serializable object;

		GroupLockValidatedEntity(Serializable object) {
			this.object = object;
		}
	}

	@Override
	protected ValidatedEntity getValidatedEntity(GroupLock object) {
		return new GroupLockValidatedEntity(object);
	}
}