package org.rumusanframework.concurrent;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
public abstract class FinishedStateThread extends Thread {
	private boolean finished = false;

	@Override
	public final void run() {
		setPriority(NORM_PRIORITY);
		try {
			doRun();
		} finally {
			finished = true;
		}
	}

	public boolean isFinished() {
		return finished;
	}

	public abstract void doRun();
}