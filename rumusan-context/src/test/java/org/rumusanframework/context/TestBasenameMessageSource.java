/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.context;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Jun 2018)
 */
public class TestBasenameMessageSource extends DefaultBasenameMessageSource {

  static final String RESOURCE_FILE_NAME = "messageResources";

  @Override
  protected String getMessageResourceFileName() {
    return RESOURCE_FILE_NAME;
  }
}