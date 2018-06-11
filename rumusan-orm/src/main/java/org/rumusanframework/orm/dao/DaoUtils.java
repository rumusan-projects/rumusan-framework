/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.orm.dao;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Id;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rumusanframework.util.ClassUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (11 Mar 2018)
 *
 */
class DaoUtils {
	private final Log logger = LogFactory.getLog(getClass());
	private Map<Class<?>, SingularAttribute<Class<?>, Class<?>>> entityMetaIdMap = new ConcurrentHashMap<>();

	SingularAttribute<Class<?>, Class<?>> getMetaAttributeId(String basePackage, Class<?> entityType) {
		SingularAttribute<Class<?>, Class<?>> attributeId = entityMetaIdMap.get(entityType);

		if (attributeId == null) {
			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
					false);
			scanner.addIncludeFilter(new AnnotationTypeFilter(StaticMetamodel.class));

			for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
				Class<?> metaClass = ClassUtils.loadClass(bd.getBeanClassName());
				Field entityPersistenceId = null;
				Class<?> entity = null;

				if (metaClass != null) {
					entity = metaClass.getAnnotation(StaticMetamodel.class).value();
					entityPersistenceId = ClassUtils.getFieldByAnnotation(entity, Id.class);

					loggerMeta(metaClass, entity, entityPersistenceId);
				}

				if (entityType.equals(entity)) {
					try {
						attributeId = ClassUtils.newInstanceFieldByClass(metaClass, entityPersistenceId);
					} catch (InstantiationException | IllegalAccessException e) {
						logger().error("Error instantiate field annotated with Id.class", e);
					}
					entityMetaIdMap.put(entityType, attributeId);

					loggerMetaAttributeId(metaClass, attributeId);

					break;
				}
			}
		}

		return attributeId;
	}

	private void loggerMeta(Class<?> metaClass, Class<?> entity, Field entityPersistenceId) {
		if (logger().isDebugEnabled()) {
			logger().debug("MetaClass : " + metaClass);
			logger().debug("Entity : " + entity);
			logger().debug("Entity Persistence Id field : " + entityPersistenceId);
		}
	}

	private void loggerMetaAttributeId(Class<?> metaClass, SingularAttribute<Class<?>, Class<?>> attributeId) {
		if (logger().isDebugEnabled() && attributeId != null) {
			logger().debug("Meta Attribute Id field : " + metaClass.getName() + "." + attributeId.getName());
		}
	}

	Log logger() {
		return logger;
	}
}