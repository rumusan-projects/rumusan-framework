package org.rumusanframework.concurrent.lock.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
@Entity
@Table(name = "GROUP_LOCK")
public class GroupLock implements Serializable {
    private static final long serialVersionUID = 4483617774089333750L;
    @Id
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "process_name")
    private String processName;
    @Column(name = "machine_name")
    private String machineName;
    @Column(name = "process_id")
    private String processId;
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
    @Column(name = "last_update_process_id")
    private String lastUpdateProcessId;
    @Column(name = "last_update_process_name")
    private String lastUpdateProcessName;

    public Long getGroupId() {
	return groupId;
    }

    public void setGroupId(Long groupId) {
	this.groupId = groupId;
    }

    public String getGroupName() {
	return groupName;
    }

    public void setGroupName(String groupName) {
	this.groupName = groupName;
    }

    public String getProcessName() {
	return processName;
    }

    public void setProcessName(String processName) {
	this.processName = processName;
    }

    public String getMachineName() {
	return machineName;
    }

    public void setMachineName(String machineName) {
	this.machineName = machineName;
    }

    public String getProcessId() {
	return processId;
    }

    public void setProcessId(String processId) {
	this.processId = processId;
    }

    public Date getLastUpdateTime() {
	return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
	this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateProcessId() {
	return lastUpdateProcessId;
    }

    public void setLastUpdateProcessId(String lastUpdateProcessId) {
	this.lastUpdateProcessId = lastUpdateProcessId;
    }

    public String getLastUpdateProcessName() {
	return lastUpdateProcessName;
    }

    public void setLastUpdateProcessName(String lastUpdateProcessName) {
	this.lastUpdateProcessName = lastUpdateProcessName;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	GroupLock other = (GroupLock) obj;
	if (groupId == null) {
	    if (other.groupId != null)
		return false;
	} else if (!groupId.equals(other.groupId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("GroupLock [groupId=");
	builder.append(groupId);
	builder.append(", groupName=");
	builder.append(groupName);
	builder.append(", processName=");
	builder.append(processName);
	builder.append(", machineName=");
	builder.append(machineName);
	builder.append(", processId=");
	builder.append(processId);
	builder.append(", lastUpdateTime=");
	builder.append(lastUpdateTime);
	builder.append(", lastProcessId=");
	builder.append(lastUpdateProcessId);
	builder.append(", lastUpdateProcessName=");
	builder.append(lastUpdateProcessName);
	builder.append("]");
	return builder.toString();
    }
}