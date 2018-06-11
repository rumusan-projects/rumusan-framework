/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (8 Jun 2018)
 *
 */
public class ProcessContextTest {
	@Test
	public void testSetGetObject() {
		ProcessContext<String> context = new ProcessContext<>();

		assertNull(context.getObject());

		String value = "value";
		context.setObject(value);

		String actual = context.getObject();
		assertNotNull(actual);
		assertEquals(value, actual);
	}
}