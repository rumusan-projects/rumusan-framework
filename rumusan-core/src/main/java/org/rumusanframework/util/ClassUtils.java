/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	public static Field[] getAllField(Class<?> classToScan) {
		Set<Field> fieldSet = new HashSet<>();
		fieldSet.addAll(Arrays.asList(classToScan.getDeclaredFields()));

		Class<?> superClass = classToScan.getSuperclass();

		while (isValidClassToScan(superClass)) {
			fieldSet.addAll(Arrays.asList(superClass.getDeclaredFields()));

			superClass = superClass.getSuperclass();
		}

		return fieldSet.toArray(new Field[fieldSet.size()]);
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
			if (logger().isErrorEnabled()) {
				logger().error("Error while load class.", e);
			}
		}

		return null;
	}

	public static <T> T newInstanceFieldByClass(Class<?> clazz, Field field) {
		if (clazz != null && field != null) {
			Field[] fields = clazz.getDeclaredFields();

			for (Field innerField : fields) {
				if (innerField.getName().equals(field.getName())) {
					return newInstance(clazz, innerField);
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T> T newInstance(Class<?> clazz, Field field) {
		try {
			return (T) field.get(clazz.newInstance());
		} catch (Exception e) {
			if (logger().isErrorEnabled()) {
				logger().error("Error initiating object.", e);
			}
		}

		return null;
	}

	static Log logger() {
		return LOGGER;
	}
}