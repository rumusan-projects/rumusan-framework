/*
 * Copyright 29 Nov 2015 the original author or authors.
 */

package org.rumusanframework.concurrent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (29 Nov 2015)
 * 
 */
public class ManagedTask implements Runnable {
	private static final Log LOGGER = LogFactory.getLog(ManagedTask.class);
	private TaskEvent event;
	private TaskEvent finalEvent;

	public ManagedTask(TaskEvent event) {
		this.event = event;
	}

	void setFinalEvent(TaskEvent finalEvent) {
		this.finalEvent = finalEvent;
	}

	public final void run() {
		try {
			event.execute();
		} catch (Exception e) {
			if (logger().isErrorEnabled()) {
				logger().error("Error on executing.", e);
			}
		} finally {
			if (finalEvent != null) {
				executeFinalEvent();
			}
		}
	}

	private void executeFinalEvent() {
		try {
			finalEvent.execute();
		} catch (Exception e) {
			if (logger().isErrorEnabled()) {
				logger().error("Error on executing final event.", e);
			}
		}
	}

	Log logger() {
		return LOGGER;
	}
}