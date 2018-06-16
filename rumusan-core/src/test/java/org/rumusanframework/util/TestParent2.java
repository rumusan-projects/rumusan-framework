package org.rumusanframework.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TestParent2 {
	@FieldParent1
	private String fieldParent1;
	@DifferentField
	private Long differentField;

	public String getFieldParent1() {
		return fieldParent1;
	}

	public void setFieldParent1(String fieldParent1) {
		this.fieldParent1 = fieldParent1;
	}

	public Long getDifferentField() {
		return differentField;
	}

	public void setDifferentField(Long differentField) {
		this.differentField = differentField;
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface FieldParent1 {
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DifferentField {
	}
}