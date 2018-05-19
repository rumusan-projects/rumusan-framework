package org.rumusanframework.concurrent.lock;

import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rumusanframework.concurrent.FinishedExecutorService;
import org.rumusanframework.concurrent.FinishedStateThread;
import org.rumusanframework.concurrent.lock.config.LockGroupConfig;
import org.rumusanframework.concurrent.lock.context.LockingProcess;
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
	@Autowired
	private LockingProcess<GroupLock> optimisticLockingProcess;

	@Test
	public void testExecute() {
		System.out.println("Real processor core : " + Runtime.getRuntime().availableProcessors());
		int processorCore = 10;
		System.out.println("Simulate processor core : " + processorCore);

		final Set<Integer> successSet = new ConcurrentSkipListSet<>();
		final Set<Integer> failedSet = new ConcurrentSkipListSet<>();

		final FinishedStateThread[] threads = new FinishedStateThread[10];
		FinishedExecutorService myExecutor = new FinishedExecutorService(processorCore, threads, 10);
		Long startTime = System.currentTimeMillis();

		for (int i = 0; i < threads.length; i++) {
			final int id = i;

			threads[i] = new FinishedStateThread() {
				@Override
				public void doRun() {
					Long start = System.currentTimeMillis();

					ProcessContext<GroupLock> context = new ProcessContext<>();
					try {
						optimisticLockingProcess.execute(context);
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
		}

		myExecutor.execute();
		myExecutor.shutDown();
		myExecutor.awaitTermination();

		Long endTime = System.currentTimeMillis();
		System.out.println("Elapsed in : " + (endTime - startTime) + " ms.");

		assertTrue(!successSet.isEmpty());
		assertTrue(!failedSet.isEmpty());
	}

	private void addSet(Set<Integer> map, int id) {
		if (map.contains(id)) {
			throw new RuntimeException("Found object with the same key : " + id);
		} else {
			map.add(id);
		}
	}
}