/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @param <R>
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (18 Mar 2018)
 * 
 */
public interface DtoDao<R> {
	public R findDtoById(Serializable id);

	public List<R> findAllDto();
}