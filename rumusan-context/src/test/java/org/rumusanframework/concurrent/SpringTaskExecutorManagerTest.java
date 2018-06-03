/*
 * Copyright 29 Nov 2015 - 2018 the original author or authors.
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
		taskExecutor.initialize();
		int interval = 500;
		SpringTaskExecutorManager manager = new SpringTaskExecutorManager(taskExecutor, interval);
		ManagedTask task = new ManagedTask(new TaskEvent() {
			@Override
			public void execute() {
			}
		});
		manager.addTask(task);

		Assert.assertTrue(manager.getTask().contains(task));
		Assert.assertEquals(1, manager.getTask().size());
	}

	@Test
	public void testExecuteAndWaitTasks() throws InterruptedException {
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
		Assert.assertEquals(0, manager.getTask().size());

		manager.executeAndWaitTasks();
	}

	@Test
	public void testIsShutdown() throws InterruptedException {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.initialize();
		int interval = 10;
		SpringTaskExecutorManager manager = new SpringTaskExecutorManager(taskExecutor, interval);

		manager.executeAndWaitTasks();
		Assert.assertEquals(0, manager.getTask().size());

		manager = new SpringTaskExecutorManager(taskExecutor, interval);
		taskExecutor.shutdown();
		manager.executeAndWaitTasks();
		Assert.assertEquals(0, manager.getTask().size());
	}
}