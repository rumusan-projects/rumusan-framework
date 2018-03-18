/*
 * Copyright 29 Nov 2015 the original author or authors.
 */

package org.rumusanframework.util.concurrent.executor;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.core.Appender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ManagedTaskTest {
    @Mock
    private Log log;
    @Mock
    private Appender mockAppender;

    class EventValue {
	private String value;
	private boolean isFinished;

	void setValue(String value) {
	    this.value = value;
	}

	String getValue() {
	    return value;
	}

	boolean isFinished() {
	    return isFinished;
	}

	void setFinished(boolean isFinished) {
	    this.isFinished = isFinished;
	}
    }

    @Before
    public void setup() {
    }

    /*
     * Ensure event is executed by changing object value from "empty" to "filled"
     */
    @Test
    public void testRun() {
	final EventValue eventValue = new EventValue();
	eventValue.setValue("empty");

	Assert.assertEquals("empty", eventValue.getValue());

	TaskEvent event = new TaskEvent() {
	    @Override
	    public void execute() {
		eventValue.setValue("filled");
		eventValue.setFinished(true);
	    }
	};
	ManagedTask task = new ManagedTask(event);
	task.run();

	while (!eventValue.isFinished()) {
	}

	Assert.assertEquals("filled", eventValue.getValue());
    }

    /*
     * Ensure final event is executed by changing object value from "empty" to
     * "filled"
     */
    @Test
    public void testRunFinal() {
	final EventValue eventValue = new EventValue();

	TaskEvent event = new TaskEvent() {
	    @Override
	    public void execute() {
		eventValue.setFinished(true);
	    }
	};

	final EventValue eventValueFinal = new EventValue();
	eventValueFinal.setValue("empty");

	Assert.assertEquals("empty", eventValueFinal.getValue());
	TaskEvent finalEvent = new TaskEvent() {
	    @Override
	    public void execute() {
		eventValueFinal.setValue("filled");
	    }
	};

	ManagedTask task = new ManagedTask(event);
	task.setFinalEvent(finalEvent);
	task.run();

	while (!eventValue.isFinished()) {
	}

	Assert.assertTrue(eventValue.isFinished());
	Assert.assertEquals("filled", eventValueFinal.getValue());
    }

    /*
     * Coverage for error logger
     */
    @Test
    public void testRunWithException() {
	final EventValue eventValue = new EventValue();

	TaskEvent event = new TaskEvent() {
	    @Override
	    public void execute() {
		eventValue.setFinished(true);
		throw new RuntimeException("Error runtime");
	    }
	};
	TaskEvent finalEvent = new TaskEvent() {
	    @Override
	    public void execute() {
		throw new RuntimeException("Error runtime");
	    }
	};

	ManagedTask task = Mockito.spy(new ManagedTask(event));
	task.setFinalEvent(finalEvent);

	Mockito.when(task.logger()).thenReturn(log);
	Mockito.when(log.isErrorEnabled()).thenReturn(true);
	task.run();
	while (!eventValue.isFinished()) {
	}

	Mockito.when(log.isErrorEnabled()).thenReturn(false);
	task.run();
	while (!eventValue.isFinished()) {
	}

	Assert.assertTrue(eventValue.isFinished());
    }
}