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
 * @since 1.0.0 (11 Mar 2018)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Lock {

  Class<?> keyValueGroupClass();

  boolean ignoreSameProcess() default false;
}