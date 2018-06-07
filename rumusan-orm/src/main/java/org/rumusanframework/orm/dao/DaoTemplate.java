/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.rumusanframework.validation.ConstraintViolationUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 * @param <E>
 *            Entity type
 */
public abstract class DaoTemplate<E extends Serializable> implements IGenericDao<E> {
	private final Log logger = LogFactory.getLog(DaoTemplate.class);
	protected Class<E> entityType = getEntityClass();
	protected DaoUtils daoUtils = new DaoUtils();
	private Validator validator;

	/**
	 * Please add an annotation {@link PersistenceContext} with specific unitName.
	 * 
	 * @param entityManager
	 */
	protected abstract void setEntityManager(EntityManager entityManager);

	protected abstract EntityManager getEntityManager();

	@SuppressWarnings("unchecked")
	private Class<E> getEntityClass() {
		if (entityType == null) {
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			return (Class<E>) thisType.getActualTypeArguments()[0];
		}

		return entityType;
	}

	public Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public void refresh(E object) {
		validateContext(object);
		getSession().refresh(object);
	}

	public Serializable save(E object) {
		validateContext(object);
		return getSession().save(object);
	}

	protected abstract ValidatedEntity getValidatedEntity(E object);

	private void validateContext(E object) {
		ValidatedEntity entity = getValidatedEntity(object);

		if (entity != null) {
			ConstraintViolationUtils.performChecking(validator.validate(entity));
		}
	}

	public void update(E object) {
		validateContext(object);
		getSession().update(object);
	}

	public void delete(E object) {
		validateContext(object);
		getSession().delete(object);
	}

	@Override
	public E findById(Serializable id) {
		return getSession().get(entityType, id);
	}

	protected CriteriaQuery<E> getEntityCriteriaQuery() {
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(entityType);
		cq.from(entityType);

		return cq;
	}

	/**
	 * Fastest than :<br/>
	 * <code>
	 * return getSession().createCriteria(clazz).list();
	 * </code>
	 */
	@Override
	public List<E> findAll() {
		return getSession().createQuery(getEntityCriteriaQuery()).getResultList();
	}

	@Override
	public Date getSystemDate() {
		return new Date();
	}

	Log logger() {
		return logger;
	}
}