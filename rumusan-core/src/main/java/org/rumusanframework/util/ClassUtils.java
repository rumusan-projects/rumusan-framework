package org.rumusanframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public class ClassUtils {
    private static final Log LOGGER = LogFactory.getLog(ClassUtils.class);

    private ClassUtils() {
    }

    @SuppressWarnings("unchecked")
    public static Field getFieldByAnnotation(Class<?> classToScan, Class<?> annotationClass) {
	List<Field> fieldList = new ArrayList<>();
	fieldList.addAll(Arrays.asList(getClassField(classToScan)));

	Class<?> superClass = classToScan.getSuperclass();
	boolean isObjectClass = superClass.equals(Object.class);

	while (!isObjectClass) {
	    fieldList.addAll(Arrays.asList(getClassField(superClass)));

	    superClass = superClass.getSuperclass();
	    isObjectClass = superClass.equals(Object.class);
	}

	Field[] fields = fieldList.toArray(new Field[fieldList.size()]);

	for (Field field : fields) {
	    if (field.isAnnotationPresent((Class<? extends Annotation>) annotationClass)) {
		return field;
	    }
	}

	return null;
    }

    private static Field[] getClassField(Class<?> clazz) {
	return clazz.getDeclaredFields();
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
	    Field[] metaDeclaredFields = clazz.getDeclaredFields();

	    for (Field metaField : metaDeclaredFields) {
		if (metaField.getName().equals(field.getName())) {
		    return newInstance(clazz, metaField);
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