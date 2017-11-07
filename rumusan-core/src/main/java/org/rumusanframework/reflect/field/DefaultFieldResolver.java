package org.rumusanframework.reflect.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
abstract class DefaultFieldResolver implements FieldResolver {
    private Class<? extends Annotation>[] annotationFields;
    private Class<?> classUsage;
    protected String concater;

    @SuppressWarnings("unchecked")
    protected DefaultFieldResolver(Class<?> classUsage, Class<?>[] annotationFields) {
	this.classUsage = classUsage;
	this.annotationFields = (Class<? extends Annotation>[]) annotationFields;
	getClassCache().put(classUsage, new ConcurrentHashMap<>());
    }

    public String resolve() {
	StringBuilder buff = new StringBuilder();

	for (Class<? extends Annotation> annotationField : annotationFields) {
	    String name = getCacheFieldName(annotationField);

	    if (name != null) {
		if (buff.length() == 0) {
		    buff.append(name);
		} else {
		    buff.append(concater).append(name);
		}
	    }
	}

	return buff.toString();
    }

    public String getCacheFieldName(Class<? extends Annotation> annotationField) {
	Map<Object, String> cacheField = getClassCache().get(classUsage);

	if (!cacheField.containsKey(annotationField)) {
	    Class<?> parent = annotationField.getEnclosingClass();

	    if (parent != null) {
		Field[] fields = parent.getDeclaredFields();

		for (Field field : fields) {
		    if (field.isAnnotationPresent(annotationField)) {
			cacheField.put(annotationField, field.getName());
			break;
		    }
		}
	    }
	}

	return cacheField.get(annotationField);
    }

    protected abstract Map<Class<?>, Map<Object, String>> getClassCache();
}