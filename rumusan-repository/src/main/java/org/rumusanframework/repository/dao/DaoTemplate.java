package org.rumusanframework.repository.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.rumusanframework.validation.ConstraintViolationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 * @param <E>
 *            Entity type
 * @param <R>
 *            Return type
 */
public abstract class DaoTemplate<E extends Serializable, R> implements IGenericDao<E, R> {
    private final Log logger = LogFactory.getLog(DaoTemplate.class);
    private static final String QUERY_ROOT_NOT_FOUND = "Query Root not found.";
    private static final String ATTRIBUTE_ID_NOT_FOUND = "Attribute Id not found.";
    private DaoUtils daoUtils = new DaoUtils();
    EntityManager entityManager;
    private Class<E> entityType = getEntityClass();
    // Note : Ensure your meta model class in the same package with entity class
    private String entityPackage = entityType.getPackage().getName();
    private Validator validator;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
	if (entityType == null) {
	    ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
	    return (Class<E>) thisType.getActualTypeArguments()[0];
	}

	return entityType;
    }

    public Session getSession() {
	return (Session) entityManager.getDelegate();
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

    protected abstract CriteriaQuery<R> getVoCriteriaQuery();

    @SuppressWarnings("unchecked")
    @Override
    public R findVoById(Serializable id) {
	CriteriaBuilder cb = getSession().getCriteriaBuilder();
	CriteriaQuery<R> query = getVoCriteriaQuery();
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

    @Override
    public List<R> findAllVo() {
	return getSession().createQuery(getVoCriteriaQuery()).getResultList();
    }

    @SuppressWarnings("unchecked")
    private SingularAttribute<E, Class<?>> getAttributeId() {
	return (SingularAttribute<E, Class<?>>) daoUtils.getMetaAttributeId(entityPackage, entityType);
    }

    @Override
    public Date getSystemDate() {
	return new Date();
    }

    Log logger() {
	return logger;
    }
}