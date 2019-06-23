/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.concurrent.lock.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (16 Jun 2018)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KeyValueGroup {

  long key();

  String value();
}