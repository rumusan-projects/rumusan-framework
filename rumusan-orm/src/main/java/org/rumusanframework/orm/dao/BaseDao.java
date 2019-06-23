/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @param <E> Entity type
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 */
public interface BaseDao<E> {

  void refresh(E object);

  Serializable save(E object);

  void update(E object);

  void delete(E object);

  E findById(Serializable id);

  List<E> findAll();

  Date getSystemDate();

  E getReference(Serializable id);

  void removeProxy(E object);
}