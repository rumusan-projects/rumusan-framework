/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.context;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Jun 2018)
 *
 */
public class BaseNameMessageSourceLoaderTest {
	class ResourceBasedMessageSource extends AbstractResourceBasedMessageSource {
		@Override
		protected MessageFormat resolveCode(String code, Locale locale) {
			return null;
		}
	}

	@Test
	public void testAddBasename() {
		TestBasenameMessageSource basenameMessage = new TestBasenameMessageSource();
		Map<String, BaseNameMessageSource> map = new HashMap<>();

		map.put(basenameMessage.getClass().getName(), basenameMessage);

		ApplicationContext appContext = Mockito.mock(ApplicationContext.class);
		Mockito.when(appContext.getBeansOfType(BaseNameMessageSource.class)).thenReturn(map);
		ResourceBasedMessageSource messageSource = new ResourceBasedMessageSource();

		BaseNameMessageSourceLoader.addBasename(appContext, messageSource);
		assertEquals(1, messageSource.getBasenameSet().size());

		String result = new ArrayList<>(messageSource.getBasenameSet()).get(0);
		assertEquals(result, basenameMessage.getBaseName());

		// Mock empty auto wired
		Mockito.when(appContext.getBeansOfType(BaseNameMessageSource.class)).thenReturn(new HashMap<>());
		messageSource = new ResourceBasedMessageSource();

		BaseNameMessageSourceLoader.addBasename(appContext, messageSource);
		assertEquals(0, messageSource.getBasenameSet().size());
	}
}