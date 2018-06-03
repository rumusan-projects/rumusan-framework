/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 * @param <E>
 *            Entity type
 */
public interface IGenericDao<E> {
	public void refresh(E object);

	public Serializable save(E object);

	public void update(E object);

	public void delete(E object);

	public E findById(Serializable id);

	public List<E> findAll();

	public Date getSystemDate();
}