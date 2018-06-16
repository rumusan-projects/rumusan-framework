/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (28 Jan 2018)
 *
 */
public class ClassUtils {
	private static final Log LOGGER = LogFactory.getLog(ClassUtils.class);

	private ClassUtils() {
	}

	static Log logger() {
		return LOGGER;
	}

	public static Field[] getAllField(Class<?> classToScan) {
		Set<Field> fieldSet = new HashSet<>();
		addFields(fieldSet, classToScan.getDeclaredFields());

		Class<?> superClass = classToScan.getSuperclass();

		while (isValidClassToScan(superClass)) {
			addFields(fieldSet, superClass.getDeclaredFields());

			superClass = superClass.getSuperclass();
		}

		return fieldSet.toArray(new Field[fieldSet.size()]);
	}

	private static void addFields(Collection<Field> col, Field[] fields) {
		for (int i = 0; i < fields.length; i++) {
			if (isAllowField(fields[i])) {
				col.add(fields[i]);
			}
		}
	}

	private static boolean isAllowField(Field field) {
		return !field.isSynthetic() && !Modifier.isStatic(field.getModifiers());
	}

	private static boolean isValidClassToScan(Class<?> classToScan) {
		return !classToScan.equals(Object.class);
	}

	@SuppressWarnings("unchecked")
	public static Field getFieldByAnnotation(Class<?> classToScan, Class<?> annotationClass) {
		Field[] fields = getAllField(classToScan);

		for (Field field : fields) {
			if (field.isAnnotationPresent((Class<? extends Annotation>) annotationClass)) {
				return field;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static Field[] getFieldsByAnnotations(Class<?> classToScan, Class<?>[] annotationClasses) {
		Field[] fields = getAllField(classToScan);
		Set<Field> fieldSet = new HashSet<>();

		for (Field field : fields) {
			for (Class<?> annotationClass : annotationClasses) {
				if (field.isAnnotationPresent((Class<? extends Annotation>) annotationClass)) {
					fieldSet.add(field);
				}
			}
		}

		return fieldSet.toArray(new Field[fieldSet.size()]);
	}

	public static String[] getFieldNamesByAnnotations(Class<?> classToScan, Class<?>[] annotationClasses) {
		Field[] fields = getFieldsByAnnotations(classToScan, annotationClasses);
		Set<String> fieldNames = new HashSet<>();

		for (Field field : fields) {
			fieldNames.add(field.getName());
		}

		return fieldNames.toArray(new String[fieldNames.size()]);
	}

	public static Class<?> loadClass(String name) {
		try {
			return org.springframework.util.ClassUtils.getDefaultClassLoader().loadClass(name);
		} catch (ClassNotFoundException e) {
			logger().error("Error while load class.", e);
		}

		return null;
	}

	public static <T> T newInstanceFieldByClass(Class<?> clazz, Field field)
			throws InstantiationException, IllegalAccessException {
		if (clazz != null && field != null) {
			Field[] fields = getAllField(clazz);

			for (Field innerField : fields) {
				if (innerField.equals(field)) {
					return newInstance(innerField);
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T> T newInstance(Field field) throws InstantiationException, IllegalAccessException {
		return (T) field.getType().newInstance();
	}

	public static List<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotationClass, String basePackage) {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(annotationClass));

		List<Class<?>> result = new ArrayList<>();
		for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
			Class<?> clazz = ClassUtils.loadClass(bd.getBeanClassName());

			if (clazz != null) {
				result.add(clazz);
			}
		}
		return result;
	}
}