package org.rumusanframework.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public class FinishedExecutorService {
    private ExecutorService executorService;
    FinishedStateThread[] runnables;
    private boolean isRunning = false;
    private boolean isShutDown = false;
    private boolean isAwaitTerimnation = false;
    private int intervalChecking = 1;

    public FinishedExecutorService(int nThreads, FinishedStateThread[] runnables, Integer intervalChecking) {
	executorService = Executors.newFixedThreadPool(nThreads);
	this.runnables = runnables;
	if (intervalChecking != null) {
	    this.intervalChecking = intervalChecking;
	}
    }

    public void execute() {
	synchronized (this) {
	    if (!isRunning) {
		isRunning = true;
		for (FinishedStateThread runnable : runnables) {
		    executorService.execute(runnable);
		}
	    }
	}
    }

    public void shutDown() {
	synchronized (this) {
	    if (!isShutDown) {
		isShutDown = true;
		executorService.shutdown();
	    }
	}
    }

    public void awaitTermination() {
	synchronized (this) {
	    if (!isAwaitTerimnation) {
		isAwaitTerimnation = true;
		boolean finished = false;

		while (!finished) {
		    boolean stillAlive = false;

		    for (FinishedStateThread thread : runnables) {
			if (!thread.isFinished()) {
			    stillAlive = true;
			    break;
			}
		    }

		    if (!stillAlive) {
			finished = true;
		    }

		    try {
			Thread.sleep(intervalChecking);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
    }
}