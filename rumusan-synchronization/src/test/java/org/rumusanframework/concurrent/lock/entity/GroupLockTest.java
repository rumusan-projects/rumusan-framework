/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Jun 2018)
 *
 */
public class GroupLockTest {
	private GroupLock groupLock;

	@Before
	public void before() {
		groupLock = new GroupLock();
	}

	@Test
	public void testSetGetGroupId() {
		assertNull(groupLock.getGroupId());

		Long groupId = 10L;
		groupLock.setGroupId(groupId);

		assertNotNull(groupLock.getGroupId());
		assertEquals(groupId, groupLock.getGroupId());
	}

	@Test
	public void testSetGetGroupName() {
		assertNull(groupLock.getGroupName());

		String expected = "value";
		groupLock.setGroupName(expected);

		assertNotNull(groupLock.getGroupName());
		assertEquals(expected, groupLock.getGroupName());
	}

	@Test
	public void testSetGetProcessName() {
		assertNull(groupLock.getProcessName());

		String expected = "value";
		groupLock.setProcessName(expected);

		assertNotNull(groupLock.getProcessName());
		assertEquals(expected, groupLock.getProcessName());
	}

	@Test
	public void testSetGetMachineName() {
		assertNull(groupLock.getMachineName());

		String expected = "value";
		groupLock.setMachineName(expected);

		assertNotNull(groupLock.getMachineName());
		assertEquals(expected, groupLock.getMachineName());
	}

	@Test
	public void testSetGetProcessId() {
		assertNull(groupLock.getProcessId());

		String expected = "value";
		groupLock.setProcessId(expected);

		assertNotNull(groupLock.getProcessId());
		assertEquals(expected, groupLock.getProcessId());
	}

	@Test
	public void testSetGetLastUpdateTime() {
		assertNull(groupLock.getLastUpdateTime());

		Date expected = new Date();
		groupLock.setLastUpdateTime(expected);

		assertNotNull(groupLock.getLastUpdateTime());
		assertEquals(expected, groupLock.getLastUpdateTime());
	}

	@Test
	public void testSetGetLastUpdateProcessId() {
		assertNull(groupLock.getLastUpdateProcessId());

		String expected = "value";
		groupLock.setLastUpdateProcessId(expected);

		assertNotNull(groupLock.getLastUpdateProcessId());
		assertEquals(expected, groupLock.getLastUpdateProcessId());
	}

	@Test
	public void testSetGetLastUpdateProcessName() {
		assertNull(groupLock.getLastUpdateProcessName());

		String expected = "value";
		groupLock.setLastUpdateProcessName(expected);

		assertNotNull(groupLock.getLastUpdateProcessName());
		assertEquals(expected, groupLock.getLastUpdateProcessName());
	}

	@Test
	public void testHashCode() {
		Long groupId = 10L;
		GroupLock obj1 = new GroupLock();
		GroupLock obj2 = new GroupLock();
		assertEquals(obj1.hashCode(), obj2.hashCode());

		obj1.setGroupId(groupId);
		obj2.setGroupId(groupId);
		assertEquals(obj1.hashCode(), obj2.hashCode());
	}

	@Test
	public void testEqualsObject() {
		GroupLock obj1 = new GroupLock();
		GroupLock obj2 = null;

		assertEquals(obj1, obj1);
		assertNotEquals(obj1, obj2);

		assertNotEquals(obj1, "value");

		obj2 = new GroupLock();
		assertEquals(obj1, obj2);
		Long groupId = 10L;

		obj2.setGroupId(groupId);
		assertNotEquals(obj1, obj2);

		obj1.setGroupId(5L);
		assertNotEquals(obj1, obj2);

		obj1.setGroupId(groupId);
		assertEquals(obj1, obj2);
	}

	@Test
	public void testToString() {
		Long groupId = 5L;
		String groupName = "groupName";
		String processName = "processName";
		String machineName = "machineName";
		String processId = "processId";
		Date lastUpdateTime = new Date();
		String lastUpdateProcessId = "lastUpdateProcessId";
		String lastUpdateProcessName = "lastUpdateProcessName";

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

		GroupLock obj = new GroupLock();
		obj.setGroupId(5L);
		obj.setGroupName(groupName);
		obj.setLastUpdateProcessId(lastUpdateProcessId);
		obj.setLastUpdateProcessName(lastUpdateProcessName);
		obj.setLastUpdateTime(lastUpdateTime);
		obj.setMachineName(machineName);
		obj.setProcessId(processId);
		obj.setProcessName(processName);

		assertEquals(builder.toString(), obj.toString());
	}
}