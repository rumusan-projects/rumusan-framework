/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.context;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (28 Jan 2018)
 */
public abstract class DefaultBasenameMessageSource implements BaseNameMessageSource {

  @Override
  public final String getBaseName() {
    return getClass().getPackage().getName().concat(".").concat(getMessageResourceFileName());
  }

  protected abstract String getMessageResourceFileName();
}