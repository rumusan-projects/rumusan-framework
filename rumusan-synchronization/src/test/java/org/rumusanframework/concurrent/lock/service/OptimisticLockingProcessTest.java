package org.rumusanframework.concurrent.lock.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.rumusanframework.concurrent.lock.config.LockGroupConfig;
import org.rumusanframework.concurrent.lock.context.ProcessContext;
import org.rumusanframework.concurrent.lock.entity.GroupLock;
import org.rumusanframework.concurrent.lock.exception.ConcurrentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LockGroupConfig.class })
public class OptimisticLockingProcessTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Autowired
	private OptimisticLockingUniqueProcess optimisticLockingUniqueProcess;
	@Autowired
	private OptimisticLockingSameProcess optimisticLockingSameProcess;

	private void addSet(Set<Integer> map, int id) {
		if (map.contains(id)) {
			throw new RuntimeException("Found object with the same key : " + id);
		} else {
			map.add(id);
		}
	}

	private void awaitTermination(List<Future<?>> futures) throws InterruptedException, ExecutionException {
		for (Future<?> future : futures) {
			future.get();
		}
	}

	private void process(int threadCount, Set<Integer> successSet, Set<Integer> failedSet,
			BaseOptimisticLockingProcess process) throws InterruptedException, ExecutionException {
		int realCore = Runtime.getRuntime().availableProcessors();
		System.out.println("Real processor core : " + realCore);
		int processorCore = realCore + 1;
		System.out.println("Simulate processor core : " + processorCore);

		final Runnable[] threads = new Runnable[threadCount];
		ExecutorService executorService = Executors.newFixedThreadPool(processorCore);
		List<Future<?>> futures = new ArrayList<>(threads.length);
		Long startTime = System.currentTimeMillis();

		for (int i = 0; i < threads.length; i++) {
			final int id = i;

			threads[i] = new Runnable() {
				@Override
				public void run() {
					Long start = System.currentTimeMillis();

					ProcessContext<GroupLock> context = new ProcessContext<>();
					try {
						process.execute(context);
						Long end = System.currentTimeMillis();
						System.out.println(String.format("           %s[id:%s] elapsed in %s ms.",
								Thread.currentThread().getName(), id, (end - start)));
						addSet(successSet, id);
					} catch (ConcurrentAccessException e) {
						addSet(failedSet, id);

						System.err.println(Thread.currentThread().getName() + ". " + String.format(
								"[id:%s] Failed due concurrent process. ConcurrentProcess : %s, GroupName : %s, MachineName : %s",
								id, e.getConcurrentProcess(), e.getGroupName(), e.getMachineName()));
					} catch (Exception e) {
						addSet(failedSet, id);

						System.err.println("Failed due an exception : " + e.toString());
						e.printStackTrace();
					}
				}
			};

			futures.add(executorService.submit(threads[i]));
		}

		awaitTermination(futures);

		Long endTime = System.currentTimeMillis();
		System.out.println("Elapsed in : " + (endTime - startTime) + " ms.");
	}

	@Test
	public void testExecuteUniqueProcess() throws InterruptedException, ExecutionException {
		// double init()
		optimisticLockingUniqueProcess.init();

		final Set<Integer> successSet = new ConcurrentSkipListSet<>();
		final Set<Integer> failedSet = new ConcurrentSkipListSet<>();
		int threadCount = 10;

		process(threadCount, successSet, failedSet, optimisticLockingUniqueProcess);

		assertTrue(!successSet.isEmpty());
		assertTrue(!failedSet.isEmpty());
	}

	@Test
	public void testExecuteSameProcess() throws InterruptedException, ExecutionException {
		final Set<Integer> successSet = new ConcurrentSkipListSet<>();
		final Set<Integer> failedSet = new ConcurrentSkipListSet<>();
		int threadCount = 10;

		process(threadCount, successSet, failedSet, optimisticLockingSameProcess);

		assertTrue(!successSet.isEmpty());
		assertEquals(successSet.size(), threadCount);
		assertTrue(failedSet.isEmpty());
	}

	@Test
	public void testInvalidProcess() {
		expectedException.expect(RuntimeException.class);
		String expectMessage = "Not a valid synchronize process : " + OptimisticLockingInvalidProcess.class.getName();
		expectedException.expectMessage(expectMessage);

		OptimisticLockingInvalidProcess optimisticLockingInvalidProcess = new OptimisticLockingInvalidProcess();
		optimisticLockingInvalidProcess.init();
	}
}