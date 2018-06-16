/*
 * Copyright 2018-2018 the original author or authors.
 */

package org.rumusanframework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.rumusanframework.util.TestParent.FieldParent1;
import org.rumusanframework.util.TestParent.FieldParent2;
import org.rumusanframework.util.TestParent.SelectedField;
import org.rumusanframework.util.TestParent2.DifferentField;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Jun 2018)
 *
 */
public class ClassUtilsTest {
	@Test
	public void testGetAllField() {
		Field[] parentFields = ClassUtils.getAllField(TestParent.class);
		assertEquals(3, parentFields.length);

		Field[] chiledFields = ClassUtils.getAllField(TestChild.class);
		assertEquals(5, chiledFields.length);
	}

	@Test
	public void testGetFieldByAnnotation() {
		Field parentField = ClassUtils.getFieldByAnnotation(TestParent.class, FieldParent1.class);
		assertNotNull(parentField);

		Field unknownField = ClassUtils.getFieldByAnnotation(TestParent.class, FieldParent2.class);
		assertNull(unknownField);
	}

	@Test
	public void testGetFieldsByAnnotations() {
		Class<?>[] annotations = new Class<?>[] { SelectedField.class };
		Field[] fields = ClassUtils.getFieldsByAnnotations(TestParent.class, annotations);
		assertNotNull(fields);
		assertEquals(2, fields.length);

		annotations = new Class<?>[] { FieldParent2.class };
		fields = ClassUtils.getFieldsByAnnotations(TestParent.class, annotations);
		assertNotNull(fields);
		assertEquals(0, fields.length);
	}

	@Test
	public void testGetFieldNamesByAnnotations() {
		Class<?>[] annotations = new Class<?>[] { SelectedField.class };
		String[] names = ClassUtils.getFieldNamesByAnnotations(TestParent.class, annotations);
		assertNotNull(names);
		assertEquals(2, names.length);
	}

	@Test
	public void testLoadClass() {
		Class<?> clazz = ClassUtils.loadClass("org.rumusanframework.util.TestParent");
		assertNotNull(clazz);
		assertEquals(TestParent.class, clazz);
	}

	@Test
	public void testLoadClassError() {
		Class<?> clazz = ClassUtils.loadClass("org.rumusanframework.util.234234");
		assertNull(clazz);
	}

	@Test
	public void testNewInstanceSameFieldNameByClass() throws InstantiationException, IllegalAccessException {
		TestParent obj = new TestParent();
		Field field = ClassUtils.getFieldByAnnotation(obj.getClass(), FieldParent1.class);

		assertNull(obj.getFieldParent1());
		String value = ClassUtils.newInstanceSameFieldNameByClass(obj.getClass(), field);
		assertNotNull(value);

		value = ClassUtils.newInstanceSameFieldNameByClass(null, field);
		assertNull(value);

		value = ClassUtils.newInstanceSameFieldNameByClass(obj.getClass(), null);
		assertNull(value);

		// test for same field name different class
		TestParent2 obj2 = new TestParent2();
		String value2 = ClassUtils.newInstanceSameFieldNameByClass(obj2.getClass(), field);
		assertNotNull(value2);

		Field differentField = ClassUtils.getFieldByAnnotation(obj2.getClass(), DifferentField.class);
		value2 = ClassUtils.newInstanceSameFieldNameByClass(obj.getClass(), differentField);
		assertNull(value2);
	}

	/**
	 * Using {@link ClassByAnnotation}
	 * 
	 * @author Harvan Irsyadi
	 *
	 */
	@Test
	public void testGetClassByAnnotation() {
		List<Class<?>> classList = ClassUtils.getClassByAnnotation(AnnotationTest.class,
				getClass().getPackage().getName());

		assertNotNull(classList);
		assertFalse(classList.isEmpty());
		assertEquals(1, classList.size());
	}
}