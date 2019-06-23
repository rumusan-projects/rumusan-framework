/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.context;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 */
public class ProcessContext<T> {

  private T object;

  public T getObject() {
    return object;
  }

  public void setObject(T object) {
    this.object = object;
  }
}