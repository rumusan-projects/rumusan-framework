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
		/**
		 * <pre>
		 * 1. private String fieldParent1;
		 * 2. private Long fieldParent2;
		 * 3. public static String CONSTANT2 = "";
		 * 4. private final Long finalField = 10L;
		 * </pre>
		 */
		Field[] parentFields = ClassUtils.getAllField(TestParent.class);
		assertEquals(4, parentFields.length);

		/**
		 * <pre>
		 * 1. private String fieldParent1;
		 * 2. private Long fieldParent2;
		 * 3. public static String CONSTANT2 = "";
		 * 4. private final Long finalField = 10L;
		 * 5. private Long fieldChild1;
		 * 6. private String fieldChild2;
		 * </pre>
		 */
		Field[] chiledFields = ClassUtils.getAllField(TestChild.class);
		assertEquals(6, chiledFields.length);
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
	public void testGetStaticInstanceSameFieldNameByClass_nonStatic()
			throws InstantiationException, IllegalAccessException {
		TestParent obj = new TestParent();
		Field field = ClassUtils.getFieldByAnnotation(obj.getClass(), FieldParent1.class);

		assertNull(obj.getFieldParent1());

		String value = ClassUtils.getStaticInstanceSameFieldNameByClass(obj.getClass(), field);
		assertNull(value);

		obj.setFieldParent1("value1");
		value = ClassUtils.getStaticInstanceSameFieldNameByClass(obj.getClass(), field);
		assertNull(value);

		value = ClassUtils.getStaticInstanceSameFieldNameByClass(null, field);
		assertNull(value);

		value = ClassUtils.getStaticInstanceSameFieldNameByClass(obj.getClass(), null);
		assertNull(value);

		// different field
		Field differentField = ClassUtils.getFieldByAnnotation(TestParent2.class, DifferentField.class);
		String value2 = ClassUtils.getStaticInstanceSameFieldNameByClass(obj.getClass(), differentField);
		assertNull(value2);
	}

	@Test
	public void testGetStaticInstanceSameFieldNameByClass_static()
			throws InstantiationException, IllegalAccessException {
		Field field = ClassUtils.getFieldByAnnotation(TestParent.class, FieldParent1.class);
		String fieldParent1Value = "fieldParent1";
		TestParent2.fieldParent1 = fieldParent1Value;
		String value2 = ClassUtils.getStaticInstanceSameFieldNameByClass(TestParent2.class, field);

		assertNotNull(value2);
		assertEquals(fieldParent1Value, value2);
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