package org.rumusanframework.repository.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
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