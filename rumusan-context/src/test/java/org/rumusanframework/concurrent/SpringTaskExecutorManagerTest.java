/*
 * Copyright 29 Nov 2015 the original author or authors.
 */

package org.rumusanframework.concurrent;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (29 Nov 2015)
 *
 */
public class SpringTaskExecutorManagerTest {
	@Test
	public void testSpringTaskExecutorManagerThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		new SpringTaskExecutorManager(taskExecutor);
	}

	@Test
	public void testSpringTaskExecutorManagerThreadPoolTaskExecutorInt() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		new SpringTaskExecutorManager(taskExecutor, 500);
	}

	@Test
	public void testGetTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		SpringTaskExecutorManager manager = new SpringTaskExecutorManager(taskExecutor);
		Assert.assertEquals(taskExecutor, manager.getTaskExecutor());
	}

	@Test
	public void testGetIntervalFinishedChecker() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		int interval = 500;
		SpringTaskExecutorManager manager = new SpringTaskExecutorManager(taskExecutor, interval);
		Assert.assertEquals(interval, manager.getIntervalFinishedChecker());
	}

	@Test
	public void testSetIntervalFinishedChecker() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		int interval = 500;
		SpringTaskExecutorManager manager = new SpringTaskExecutorManager(taskExecutor);
		manager.setIntervalFinishedChecker(interval);

		Assert.assertEquals(interval, manager.getIntervalFinishedChecker());

	}

	@Test
	public void testAddTaskGetTask() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		int interval = 500;
		SpringTaskExecutorManager manager = new SpringTaskExecutorManager(taskExecutor, interval);
		ManagedTask task = new ManagedTask(new TaskEvent() {
			@Override
			public void execute() {
			}
		});
		manager.addTask(task);

		Assert.assertTrue(manager.getTask().contains(task));
		Assert.assertTrue(manager.getTask().size() == 1);
	}

	@Test
	public void testExecuteAndWaitTasks() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		int interval = 10;
		SpringTaskExecutorManager manager = new SpringTaskExecutorManager(taskExecutor, interval);
		ManagedTask task = new ManagedTask(new TaskEvent() {
			@Override
			public void execute() {
			}
		});
		manager.addTask(task);

		manager.executeAndWaitTasks();
		Assert.assertTrue(manager.getTask().size() == 0);

		manager.executeAndWaitTasks();
	}
}