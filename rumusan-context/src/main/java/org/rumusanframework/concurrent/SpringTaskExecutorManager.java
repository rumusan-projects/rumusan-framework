/*
 * Copyright 29 Nov 2015 the original author or authors.
 */

package org.rumusanframework.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (29 Nov 2015)
 * 
 */
public class SpringTaskExecutorManager {
	private final Log logger = LogFactory.getLog(getClass());
	private final List<ManagedTask> tasks = new CopyOnWriteArrayList<>();
	private int intervalFinishedChecker = 100;
	private ThreadPoolTaskExecutor taskExecutor;
	private boolean isStarting = false;

	public SpringTaskExecutorManager(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public SpringTaskExecutorManager(ThreadPoolTaskExecutor taskExecutor, int intervalFinishedChecker) {
		this.taskExecutor = taskExecutor;
		this.intervalFinishedChecker = intervalFinishedChecker;
	}

	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public int getIntervalFinishedChecker() {
		return intervalFinishedChecker;
	}

	public void setIntervalFinishedChecker(int intervalFinishedChecker) {
		this.intervalFinishedChecker = intervalFinishedChecker;
	}

	public boolean addTask(final ManagedTask task) {
		task.setFinalEvent(new TaskEvent() {// NOSONAR
			public void execute() {
				tasks.remove(task);
			}
		});

		return tasks.add(task);
	}

	List<ManagedTask> getTask() {
		return Collections.unmodifiableList(new ArrayList<>(tasks));
	}

	private void executeTasks() {
		try {
			if (taskExecutor.getThreadPoolExecutor().isShutdown()) {
				taskExecutor.initialize();
			}
		} catch (IllegalStateException e) {
			taskExecutor.initialize();
		}

		for (ManagedTask task : tasks) {
			taskExecutor.execute(task);
		}
	}

	private void waitAllTaskFinished() {
		try {
			while (!tasks.isEmpty()) {
				Thread.sleep(intervalFinishedChecker);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Error on waitAllTaskFinished.", e);
			}
		}
	}

	public synchronized void executeAndWaitTasks() {
		if (!isStarting) {
			executeTasks();
			waitAllTaskFinished();

			isStarting = true;
		}
	}
}