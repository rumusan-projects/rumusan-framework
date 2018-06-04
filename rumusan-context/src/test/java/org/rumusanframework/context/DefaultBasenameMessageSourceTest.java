/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Jun 2018)
 *
 */
public class DefaultBasenameMessageSourceTest {
	private String resourceFileName = TestBasenameMessageSource.RESOURCE_FILE_NAME;

	@Test
	public void testGetBaseName() {
		BaseNameMessageSource baseMessage = new TestBasenameMessageSource();

		assertNotNull(baseMessage.getBaseName());
		assertEquals(getClass().getPackage().getName().concat(".").concat(resourceFileName), baseMessage.getBaseName());
	}
}