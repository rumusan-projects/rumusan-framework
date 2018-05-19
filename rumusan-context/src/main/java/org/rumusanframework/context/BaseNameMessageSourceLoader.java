package org.rumusanframework.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public class BaseNameMessageSourceLoader {
	private BaseNameMessageSourceLoader() {
	}

	public static void addBasename(ApplicationContext applicationContext,
			AbstractResourceBasedMessageSource messageSource) {
		List<BaseNameMessageSource> baseNameMessageSources = getBaseNameMessageSource(applicationContext);

		if (!baseNameMessageSources.isEmpty()) {
			System.out.println("Found " + baseNameMessageSources.size() + " of autowired basename message source.");// NOSONAR
		}

		for (BaseNameMessageSource baseNameMessageSource : baseNameMessageSources) {
			System.out.println("basename : " + baseNameMessageSource.getBaseName());// NOSONAR
			messageSource.addBasenames(baseNameMessageSource.getBaseName());
		}
	}

	private static List<BaseNameMessageSource> getBaseNameMessageSource(ApplicationContext applicationContext) {
		Map<String, BaseNameMessageSource> obj = applicationContext.getBeansOfType(BaseNameMessageSource.class);

		return obj != null ? new ArrayList<>(obj.values()) : Collections.emptyList();
	}
}