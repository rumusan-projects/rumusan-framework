/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.exception;

import org.rumusanframework.concurrent.lock.context.LockingProcess;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
public class ConcurrentAccessException extends Exception {
	private static final long serialVersionUID = -5334663203727236571L;
	private final String concurrentProcess;
	private final String groupName;
	private final String machineName;

	public ConcurrentAccessException(String message, Class<? extends LockingProcess<?>> concurrentProcess,
			String groupName, String machineName) {
		super(message);
		this.concurrentProcess = concurrentProcess.getName();
		this.groupName = groupName;
		this.machineName = machineName;
	}

	public String getConcurrentProcess() {
		return concurrentProcess;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getMachineName() {
		return machineName;
	}
}