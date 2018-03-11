package org.rumusanframework.concurrent.lock.dao;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.rumusanframework.concurrent.lock.context.LockingProcess;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.entity.GroupLock_;
import org.rumusanframework.repository.dao.DaoTemplate;
import org.rumusanframework.repository.dao.ValidatedEntity;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
@Repository
public class GroupLockDao extends DaoTemplate<GroupLock, GroupLock> implements IGroupLockDao {
    private AliasToBeanResultTransformer findGroupTransformers = new AliasToBeanResultTransformer(GroupLock.class);
    private ProjectionList findAllProjection;

    @Override
    public GroupLock findGroup(Long groupId, Class<?> processClass, boolean ignoreSameProcess) {
	Criteria criteria = getSession().createCriteria(GroupLock.class);

	criteria.add(Restrictions.eq(GroupLock_.GROUP_ID, groupId));
	if (ignoreSameProcess) {
	    criteria.add(Restrictions.or(Restrictions.eq(GroupLock_.PROCESS_NAME, processClass.getName()),
		    Restrictions.isNull(GroupLock_.PROCESS_NAME)));
	} else {
	    criteria.add(Restrictions.isNull(GroupLock_.PROCESS_NAME));
	}

	if (findAllProjection == null) {
	    findAllProjection = Projections.projectionList();
	    findAllProjection.add(Projections.property(GroupLock_.GROUP_ID), GroupLock_.GROUP_ID);
	    findAllProjection.add(Projections.property(GroupLock_.GROUP_NAME), GroupLock_.GROUP_NAME);
	    findAllProjection.add(Projections.property(GroupLock_.PROCESS_NAME), GroupLock_.PROCESS_NAME);
	    findAllProjection.add(Projections.property(GroupLock_.MACHINE_NAME), GroupLock_.MACHINE_NAME);
	    findAllProjection.add(Projections.property(GroupLock_.PROCESS_ID), GroupLock_.PROCESS_ID);
	    findAllProjection.add(Projections.property(GroupLock_.LAST_UPDATE_TIME), GroupLock_.LAST_UPDATE_TIME);
	    findAllProjection.add(Projections.property(GroupLock_.LAST_UPDATE_PROCESS_NAME),
		    GroupLock_.LAST_UPDATE_PROCESS_NAME);
	    findAllProjection.add(Projections.property(GroupLock_.LAST_UPDATE_PROCESS_ID),
		    GroupLock_.LAST_UPDATE_PROCESS_ID);
	}

	criteria.setProjection(findAllProjection);
	criteria.setResultTransformer(findGroupTransformers);

	return (GroupLock) criteria.uniqueResult();
    }

    private static final String UPDATE_SAME_PROCESS = "update GroupLock set processName = :processName"
	    + ", machineName = :machineName, processId = :processId, lastUpdateTime = :lastUpdateTime"
	    + ", lastUpdateProcessName = :lastUpdateProcessName, lastUpdateProcessId = :lastUpdateProcessId "
	    + "where groupId = :key1 and (processName is null or processName = :key2)";
    private static final String UPDATE_UNIQUE_PROCESS = "update GroupLock set processName = :processName"
	    + ", machineName = :machineName, processId = :processId, lastUpdateTime = :lastUpdateTime"
	    + ", lastUpdateProcessName = :lastUpdateProcessName, lastUpdateProcessId = :lastUpdateProcessId "
	    + "where groupId = :key1 and (processId is null or processId = :key2)";

    @Override
    public int optimisticCheckIn(GroupLock groupLock, Class<? extends LockingProcess<GroupLock>> classCaller,
	    boolean ignoreSameProces) {
	Query<?> query = null;

	if (ignoreSameProces) {
	    query = getSession().createQuery(UPDATE_SAME_PROCESS);

	    setQueryParameter(query, groupLock);

	    query.setParameter("key1", groupLock.getGroupId());
	    query.setParameter("key2", groupLock.getProcessName());

	    return query.executeUpdate();
	} else if (groupLock.getProcessName() != null || groupLock.getProcessId() != null
		|| groupLock.getMachineName() != null) {
	    query = getSession().createQuery(UPDATE_UNIQUE_PROCESS);

	    setQueryParameter(query, groupLock);

	    query.setParameter("key1", groupLock.getGroupId());
	    query.setParameter("key2", groupLock.getProcessId());

	    return query.executeUpdate();
	}

	return 0;
    }

    private void setQueryParameter(Query<?> query, GroupLock groupLock) {
	query.setParameter(GroupLock_.PROCESS_NAME, groupLock.getProcessName());
	query.setParameter(GroupLock_.MACHINE_NAME, groupLock.getMachineName());
	query.setParameter(GroupLock_.PROCESS_ID, groupLock.getProcessId());
	query.setParameter(GroupLock_.LAST_UPDATE_TIME, groupLock.getLastUpdateTime());
	query.setParameter(GroupLock_.LAST_UPDATE_PROCESS_NAME, groupLock.getLastUpdateProcessName());
	query.setParameter(GroupLock_.LAST_UPDATE_PROCESS_ID, groupLock.getLastUpdateProcessId());

    }

    private static final String UPDATE_RESET_LOCK = "update GroupLock set processName = :processName, machineName = :machineName"
	    + ", processId = :processId where processId = :key";

    @Override
    public int resetLock(GroupLock groupLock) {
	Query<?> query = getSession().createQuery(UPDATE_RESET_LOCK);

	query.setParameter(GroupLock_.PROCESS_NAME, null);
	query.setParameter(GroupLock_.MACHINE_NAME, null);
	query.setParameter(GroupLock_.PROCESS_ID, null);
	query.setParameter("key", groupLock.getProcessId());

	return query.executeUpdate();
    }

    @Override
    protected CriteriaQuery<GroupLock> getVoCriteriaQuery() {
	return null;
    }

    @Override
    protected ValidatedEntity getValidatedEntity(GroupLock object) {
	return null;
    }
}