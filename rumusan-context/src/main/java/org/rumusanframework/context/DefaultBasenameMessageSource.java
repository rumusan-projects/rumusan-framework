package org.rumusanframework.context;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public abstract class DefaultBasenameMessageSource implements BaseNameMessageSource {
	@Override
	public final String getBaseName() {
		return getClass().getPackage().getName().concat(".").concat(getMessageResourceFileName());
	}

	protected abstract String getMessageResourceFileName();
}