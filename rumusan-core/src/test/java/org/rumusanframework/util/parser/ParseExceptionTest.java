package org.rumusanframework.util.parser;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Harvan Irsyad
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class ParseExceptionTest {
	@Test
	public void testParseExceptionNullMessageNullCause() {
		try {
			new ParseException();
		} catch (ParseException pe) {
			Assert.assertNull(pe.getMessage());
			Assert.assertNull(pe.getCause());
		}
		printMethod(new Object() {
		}, null);
	}

	@Test
	public void testParseExceptionNullCause() {
		String message = "Parse message";

		try {
			new ParseException(message);
		} catch (ParseException pe) {
			Assert.assertNotNull(pe.getMessage());
			Assert.assertEquals(message, pe.getMessage());
			Assert.assertNull(pe.getCause());
		}
		printMethod(new Object() {
		}, null);
	}

	@Test
	public void testParseExceptionNullMessage() {
		Exception cause = null;
		String causeMessage = "Exception cause";

		try {
			cause = new Exception(causeMessage);
		} catch (Exception e) {
		}

		try {
			new ParseException(cause);
		} catch (ParseException pe) {
			Assert.assertNull(pe.getMessage());
			Assert.assertNotNull(pe.getCause());
			Assert.assertEquals(causeMessage, pe.getCause().getMessage());
		}
		printMethod(new Object() {
		}, null);
	}

	@Test
	public void testParseException() {
		Exception cause = null;
		String causeMessage = "Exception cause";
		String message = "Parse message";

		try {
			cause = new Exception(causeMessage);
		} catch (Exception e) {
		}

		try {
			new ParseException(message, cause);
		} catch (ParseException pe) {
			Assert.assertNotNull(pe.getMessage());
			Assert.assertEquals(message, pe.getMessage());
			Assert.assertNotNull(pe.getCause());
			Assert.assertEquals(causeMessage, pe.getCause().getMessage());
		}
		printMethod(new Object() {
		}, null);
	}

	private void printMethod(Object obj, String additionalString) {
		String name = obj.getClass().getEnclosingMethod().getName();

		System.out.println("End " + name + (additionalString != null ? additionalString : ""));
	}
}