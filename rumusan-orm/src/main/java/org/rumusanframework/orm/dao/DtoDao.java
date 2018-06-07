/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.util.Assert;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (18 Mar 2018)
 *
 */
public abstract class DtoDao<E extends Serializable, R> extends DaoTemplate<E> implements IDtoDao<R> {
	private static final String QUERY_ROOT_NOT_FOUND = "Query Root not found.";
	private static final String ATTRIBUTE_ID_NOT_FOUND = "Attribute Id not found.";
	// Note : Ensure your meta model class in the same package with entity class
	private String entityPackage = entityType.getPackage().getName();

	@SuppressWarnings("unchecked")
	private SingularAttribute<E, Class<?>> getAttributeId() {
		return (SingularAttribute<E, Class<?>>) daoUtils.getMetaAttributeId(entityPackage, entityType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public R findDtoById(Serializable id) {
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<R> query = getDtoCriteriaQuery();
		Set<Root<?>> roots = query.getRoots();
		Root<E> queryRoot = null;

		for (Root<?> root : roots) {
			if (logger().isDebugEnabled()) {
				logger().debug("Dao entityType : " + entityType);
				logger().debug("Query Root javaType : " + root.getJavaType());
			}
			if (root.getJavaType().equals(entityType)) {
				queryRoot = (Root<E>) root;
				break;
			}
		}

		Assert.notNull(queryRoot, QUERY_ROOT_NOT_FOUND);

		SingularAttribute<E, Class<?>> attributeId = getAttributeId();
		Assert.notNull(attributeId, ATTRIBUTE_ID_NOT_FOUND);

		query.where(cb.equal(queryRoot.get(attributeId), id));

		return getSession().createQuery(query).uniqueResult();
	}

	protected abstract CriteriaQuery<R> getDtoCriteriaQuery();

	@Override
	public List<R> findAllDto() {
		return getSession().createQuery(getDtoCriteriaQuery()).getResultList();
	}
}