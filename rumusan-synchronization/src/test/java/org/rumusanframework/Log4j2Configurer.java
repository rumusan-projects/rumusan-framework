package org.rumusanframework;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class Log4j2Configurer {
    public void setLocation(String location) {
	try {
	    String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
	    URL url = ResourceUtils.getURL(resolvedLocation);

	    LoggerContext context = (LoggerContext) LogManager.getContext(false);
	    context.setConfigLocation(url.toURI());
	} catch (Exception e) {
	    throw new RuntimeException(e);// NOSONAR
	}
    }
}