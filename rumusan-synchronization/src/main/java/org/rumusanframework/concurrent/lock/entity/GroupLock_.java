package org.rumusanframework.concurrent.lock.entity;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GroupLock.class)
public class GroupLock_ {
	private GroupLock_() {
	}

	public static final String GROUP_ID = "groupId";
	public static final String GROUP_NAME = "groupName";
	public static final String PROCESS_NAME = "processName";
	public static final String MACHINE_NAME = "machineName";
	public static final String PROCESS_ID = "processId";
	public static final String LAST_UPDATE_TIME = "lastUpdateTime";
	public static final String LAST_UPDATE_PROCESS_ID = "lastUpdateProcessId";
	public static final String LAST_UPDATE_PROCESS_NAME = "lastUpdateProcessName";
	public static SingularAttribute<GroupLock, Long> groupId;
	public static SingularAttribute<GroupLock, String> groupName;
	public static SingularAttribute<GroupLock, String> processName;
	public static SingularAttribute<GroupLock, String> machineName;
	public static SingularAttribute<GroupLock, String> processId;
	public static SingularAttribute<GroupLock, Date> lastUpdateTime;
	public static SingularAttribute<GroupLock, String> lastUpdateProcessId;
	public static SingularAttribute<GroupLock, String> lastUpdateProcessName;
}