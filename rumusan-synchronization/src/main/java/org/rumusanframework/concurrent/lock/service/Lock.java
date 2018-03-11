package org.rumusanframework.concurrent.lock.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.rumusanframework.concurrent.lock.entity.GroupLockEnum;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Lock {
    GroupLockEnum groupEnum();

    boolean ignoreSameProcess() default false;
}