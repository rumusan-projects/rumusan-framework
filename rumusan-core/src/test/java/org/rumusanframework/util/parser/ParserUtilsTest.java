package org.rumusanframework.util.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.rumusanframework.util.parser.impl.DateParser;
import org.rumusanframework.util.parser.impl.ParseToFloatingChain;
import org.rumusanframework.util.parser.impl.ParseToNonFloatingChain;

/**
 * Test for java type:<br/>
 * - Byte<br/>
 * - Short<br/>
 * - Integer<br/>
 * - Long<br/>
 * - Float<br/>
 * - Double<br/>
 * - BigInteger<br/>
 * - BigDecimal<br/>
 * - Character<br/>
 * - String<br/>
 * - Boolean<br/>
 * - java.sql.Date<br/>
 * - java.sql.Timestamp<br/>
 * - java.sql.Date<br/>
 * 
 * @author Harvan Irsyadi
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ParserUtilsTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private class UnknownClass {
		private String number;

		public UnknownClass() {
		}

		public UnknownClass(String number) {
			this.number = number;
		}

		@Override
		public String toString() {
			return number;
		}
	}

	private class NewNumberClass extends Number {
		private static final long serialVersionUID = 1L;

		@Override
		public int intValue() {
			return 0;
		}

		@Override
		public long longValue() {
			return 0;
		}

		@Override
		public float floatValue() {
			return 0;
		}

		@Override
		public double doubleValue() {
			return 0;
		}
	}

	private class NewDateClass extends Date {
		private static final long serialVersionUID = 1L;
	}

	private String getMethodName(Object obj) {
		return obj.getClass().getEnclosingMethod().getName();
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<ParserUtils> constructor = ParserUtils.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testPrivateConstructorDateParser() throws Exception {
		Constructor<DateParser> constructor = DateParser.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testParseNullTargetClass() {
		System.out.println("End " + getMethodName(new Object() {
		}));
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Target class cannot be null.");

		Class<?> target = null;
		ParserUtils.parse("", target);
	}

	@Test
	public void testParseNullObject() {
		Object obj = ParserUtils.parse(null, Object.class);

		Assert.assertEquals(null, obj);
	}

	@Test
	public void testParseToUnknownClassFromString() {
		System.out.println("End " + getMethodName(new Object() {
		}));
		expectedException.expect(ParseException.class);
		expectedException.expectMessage("No wrapper found for target class : " + UnknownClass.class);

		String src = "UnknownClass";
		ParserUtils.parse(src, UnknownClass.class);
	}

	@Test
	public void testParseToByteFromStringEquals() {
		String src = "111";
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromUnknownClass() {
		Byte src = 111;
		UnknownClass obj = new UnknownClass(src.toString());
		Byte parsed = ParserUtils.parse(obj, Byte.class);

		Assert.assertEquals(new Byte(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromByteEquals() {
		Byte src = 111;
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromShortEquals() {
		Short src = 111;
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals((Byte) src.byteValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromIntegerEquals() {
		Integer src = 111;
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals((Byte) src.byteValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromLongEquals() {
		Long src = 111L;
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals((Byte) src.byteValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromFloatEquals() {
		Float src = 11.1F;
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals((Byte) src.byteValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromDoubleEquals() {
		Double src = 11.1D;
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals((Byte) src.byteValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromBigIntegerEquals() {
		BigInteger src = new BigInteger(new Long(11L).toString());
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals((Byte) src.byteValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToByteFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal(new Double(11.1D).toString());
		Byte parsed = ParserUtils.parse(src, Byte.class);

		Assert.assertEquals((Byte) src.byteValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromStringEquals() {
		String src = "11111";
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromUnknownClass() {
		Short src = 11111;
		UnknownClass obj = new UnknownClass(src.toString());
		Short parsed = ParserUtils.parse(obj, Short.class);

		Assert.assertEquals(new Short(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromByteEquals() {
		Byte src = 111;
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals((Short) src.shortValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromShortEquals() {
		Short src = 11111;
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromIntegerEquals() {
		Integer src = 11111;
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals((Short) src.shortValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromLongEquals() {
		Long src = 11111L;
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals((Short) src.shortValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromFloatEquals() {
		Float src = 1111.1F;
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals((Short) src.shortValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromDoubleEquals() {
		Double src = 1111.1D;
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals((Short) src.shortValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromBigIntegerEquals() {
		BigInteger src = new BigInteger(new Integer(11111).toString());
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals((Short) src.shortValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToShortFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal(new Float(1111.1).toString());
		Short parsed = ParserUtils.parse(src, Short.class);

		Assert.assertEquals((Short) src.shortValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromStringEquals() {
		String src = "1111111111";
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromUnknownClass() {
		Integer src = 1111111111;
		UnknownClass obj = new UnknownClass(src.toString());
		Integer parsed = ParserUtils.parse(obj, Integer.class);

		Assert.assertEquals(new Integer(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromByteEquals() {
		Byte src = 111;
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals((Integer) src.intValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromShortEquals() {
		Short src = 1111;
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals((Integer) src.intValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromIntegerEquals() {
		Integer src = 1111111111;
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromLongEquals() {
		Long src = 1111111111L;
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals((Integer) src.intValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromFloatEquals() {
		Float src = 1111111.1F;
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals((Integer) src.intValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromDoubleEquals() {
		Double src = 1111111.1D;
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals((Integer) src.intValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromBigIntegerEquals() {
		BigInteger src = new BigInteger(new Long(1111111111).toString());
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals((Integer) src.intValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToIntegerFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal(new Double(1111111111D).toString());
		Integer parsed = ParserUtils.parse(src, Integer.class);

		Assert.assertEquals((Integer) src.intValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromStringEquals() {
		String src = "1111111111";
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromUnknownClass() {
		Long src = 1111111111L;
		UnknownClass obj = new UnknownClass(src.toString());
		Long parsed = ParserUtils.parse(obj, Long.class);

		Assert.assertEquals(new Long(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromByteEquals() {
		Byte src = 111;
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals((Long) src.longValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromShortEquals() {
		Short src = 1111;
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals((Long) src.longValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromIntegerEquals() {
		Integer src = 1111111111;
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals((Long) src.longValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromLongEquals() {
		Long src = 1111111111L;
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromFloatEquals() {
		Float src = 1111111111F;
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals((Long) src.longValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromDoubleEquals() {
		Double src = 1111111111D;
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals((Long) src.longValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromBigIntegerEquals() {
		BigInteger src = new BigInteger(new Long(1111111111L).toString());
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals((Long) src.longValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToLongFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal(new Double(1111111111D).toString());
		Long parsed = ParserUtils.parse(src, Long.class);

		Assert.assertEquals((Long) src.longValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromStringEquals() {
		String src = "111111.11";
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals(new Float(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromUnknownClass() {
		Float src = 11111.11F;
		UnknownClass obj = new UnknownClass(src.toString());
		Float parsed = ParserUtils.parse(obj, Float.class);

		Assert.assertEquals(new Float(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromByteEquals() {
		Byte src = 111;
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals((Float) src.floatValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromShortEquals() {
		Short src = 1111;
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals((Float) src.floatValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromIntegerEquals() {
		Integer src = 1111111;
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals((Float) src.floatValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromLongEquals() {
		Long src = 1111111L;
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals((Float) src.floatValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromFloatEquals() {
		Float src = 111.11F;
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromDoubleEquals() {
		Double src = 111111.1D;
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals((Float) src.floatValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromBigIntegerEquals() {
		BigInteger src = new BigInteger(((Long) 1111111L).toString());
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals((Float) src.floatValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToFloatFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal(((Double) 111111.1D).toString());
		Float parsed = ParserUtils.parse(src, Float.class);

		Assert.assertEquals((Float) src.floatValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromStringEquals() {
		String src = "111.11";
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals(new Double(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromUnknownClass() {
		Double src = 11111.11D;
		UnknownClass obj = new UnknownClass(src.toString());
		Double parsed = ParserUtils.parse(obj, Double.class);

		Assert.assertEquals(new Double(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromDoubleEquals() {
		Double src = 111.11D;
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromByteEquals() {
		Byte src = 111;
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals((Double) src.doubleValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromShortEquals() {
		Short src = 11111;
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals((Double) src.doubleValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromIntegerEquals() {
		Integer src = 111111;
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals((Double) src.doubleValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromLongEquals() {
		Long src = 11111L;
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals((Double) src.doubleValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromFloatEquals() {
		Float src = 11111F;
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals((Double) src.doubleValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromBigIntegerEquals() {
		BigInteger src = new BigInteger(new Long(11111L).toString());
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals((Double) src.doubleValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDoubleFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal(new Float(1111.1F).toString());
		Double parsed = ParserUtils.parse(src, Double.class);

		Assert.assertEquals((Double) src.doubleValue(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromStringEquals() {
		String src = "1111111";
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(new BigInteger(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromUnknownClass() {
		BigInteger src = new BigInteger("1111111");
		UnknownClass obj = new UnknownClass(src.toString());
		BigInteger parsed = ParserUtils.parse(obj, BigInteger.class);

		Assert.assertEquals(new BigInteger(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromByteEquals() {
		Byte src = 111;
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(new BigInteger(src.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromShortEquals() {
		Short src = 11111;
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(new BigInteger(src.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromIntegerEquals() {
		Integer src = 1111111111;
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(new BigInteger(src.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromLongEquals() {
		Long src = 1111111111L;
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(new BigInteger(src.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromFloatEquals() {
		Float src = 1111111F;
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(BigInteger.valueOf(src.longValue()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromDoubleEquals() {
		Double src = 1111111D;
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(BigInteger.valueOf(src.longValue()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromBigIntegerEquals() {
		BigInteger src = new BigInteger("1111111");
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigIntegerFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal("111111111111111111111111111111111111111111");
		BigInteger parsed = ParserUtils.parse(src, BigInteger.class);

		Assert.assertEquals(src.toBigInteger(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromStringIntegerEquals() {
		String src = "11111111111111111111111111111111111111111111111111111111111111111111111";
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromStringDoubleEquals() {
		String src = "111111111111.11";
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromUnknownClass() {
		BigDecimal src = new BigDecimal("111111111.1111111111111111");
		UnknownClass obj = new UnknownClass(src.toString());
		BigDecimal parsed = ParserUtils.parse(obj, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(obj.toString()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromByteEquals() {
		Byte src = 111;
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromShortEquals() {
		Short src = 11111;
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromIntegerEquals() {
		Integer src = 111111111;
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromLongEquals() {
		Long src = 1111111111L;
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromFloatEquals() {
		Float src = 1111111.11F;
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromDoubleEquals() {
		Double src = 1111111.111D;
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromBigIntegerEquals() {
		BigInteger src = new BigInteger("111111111111111111111111111111111111111111111111111111111111111111111111");
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(new BigDecimal(src), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBigDecimalFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal("1111111111111.111111111111111111");
		BigDecimal parsed = ParserUtils.parse(src, BigDecimal.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToCharFromStringEquals() {
		String src = "C";
		Character parsed = ParserUtils.parse(src, Character.class);

		Assert.assertEquals((Character) src.toCharArray()[0], parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToCharFromCharEquals() {
		Character src = 'D';
		Character parsed = ParserUtils.parse(src, Character.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToCharFromStringMoreEquals() {
		String src = "CC";
		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + src);

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid character constant");

		ParserUtils.parse(src, Character.class);
	}

	@Test
	public void testParseToCharFromStringLessEquals() {
		String src = "";
		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + src);

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid character constant");

		ParserUtils.parse(src, Character.class);
	}

	@Test
	public void testParseToStringFromStringEquals() {
		String src = "1";
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBooleanFromBooleanEquals() {
		Boolean src = true;
		Boolean parsed = ParserUtils.parse(src, Boolean.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBooleanFromStringFalseEquals() {
		String src = "false";
		Boolean parsed = ParserUtils.parse(src, Boolean.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBooleanFromStringTrueEquals() {
		String src = "true";
		Boolean parsed = ParserUtils.parse(src, Boolean.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBooleanFromString1Equals() {
		String src = "1";
		Boolean parsed = ParserUtils.parse(src, Boolean.class);

		Assert.assertEquals(Boolean.TRUE.toString(), parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToBooleanFromString0Equals() {
		String src = "0";
		Boolean parsed = ParserUtils.parse(src, Boolean.class);

		Assert.assertEquals(Boolean.FALSE.toString(), parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDateFromDateEquals() {
		Date src = new Date();
		Date parsed = ParserUtils.parse(src, Date.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDateFromTimestampEquals() {
		Timestamp src = new Timestamp(System.currentTimeMillis());
		Date parsed = ParserUtils.parse(src, Date.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToDateFromSqlDateEquals() {
		java.sql.Date src = new java.sql.Date(System.currentTimeMillis());
		Date parsed = ParserUtils.parse(src, Date.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToTimestampFromStringEquals() {
		String src = "2017-09-26 21:50:00.999";
		Timestamp parsed = ParserUtils.parse(src, Timestamp.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToTimestampFromDateEquals() {
		Date src = new Date();
		Timestamp parsed = ParserUtils.parse(src, Timestamp.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToTimestampFromTimestampEquals() {
		Timestamp src = new Timestamp(System.currentTimeMillis());
		Timestamp parsed = ParserUtils.parse(src, Timestamp.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToTimestampFromSqlDateEquals() {
		java.sql.Date src = new java.sql.Date(System.currentTimeMillis());
		Timestamp parsed = ParserUtils.parse(src, Timestamp.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToSqlDateFromStringEquals() {
		String src = "2017-09-26";
		java.sql.Date parsed = ParserUtils.parse(src, java.sql.Date.class);

		Assert.assertEquals(src, parsed.toString());

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToSqlDateFromDateEquals() {
		Date src = new Date();
		java.sql.Date parsed = ParserUtils.parse(src, java.sql.Date.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToSqlDateFromTimestampEquals() {
		Timestamp src = new Timestamp(System.currentTimeMillis());
		java.sql.Date parsed = ParserUtils.parse(src, java.sql.Date.class);

		Assert.assertEquals(new java.sql.Date(src.getTime()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToSqlDateFromSqlDateEquals() {
		java.sql.Date src = new java.sql.Date(System.currentTimeMillis());
		java.sql.Date parsed = ParserUtils.parse(src, java.sql.Date.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToUnknownClassFromUnknownClass() {
		UnknownClass src = new UnknownClass();
		UnknownClass parsed = ParserUtils.parse(src, UnknownClass.class);

		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToAnyDateFromUncompatibleClass() {
		Class<?> clazz = new NewDateClass().getClass();
		System.out.println("End " + getMethodName(new Object() {
		}));
		expectedException.expect(ParseException.class);
		expectedException.expectMessage("No Date wrapper found for target class : " + clazz);

		java.sql.Date src = new java.sql.Date(System.currentTimeMillis());
		ParserUtils.parse(src, clazz);
	}

	@Test
	public void testParseToDateNullDateParser() {
		System.out.println("End " + getMethodName(new Object() {
		}));
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Parser cannot be null.");

		String src = "26-09-2017";
		IParser<Date> parser = null;
		ParserUtils.parse(src, parser);
	}

	@Test
	public void testParseToDateExceptionParser() {
		String src = "dsfasdfsdfsdfsfd";
		System.out.println("End " + getMethodName(new Object() {
		}));
		expectedException.expect(ParseException.class);
		expectedException.expectMessage("Unparseable date: \"" + src + "\"");

		IParser<Date> parser = new DateParser("dd-MM-yyyy");
		ParserUtils.parse(src, parser);
	}

	@Test
	public void testParseToDateFromDateParser() {
		String src = "26-09-2017";
		IParser<Date> parser = new DateParser("dd-MM-yyyy");
		Date parsed = ParserUtils.parse(src, parser);

		Assert.assertEquals(new Date(parsed.getTime()), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParseToNewNumberClassFromUnknownClass() {
		expectedException.expect(ParseException.class);
		expectedException.expectMessage("No wrapper found for target class : " + new NewNumberClass().getClass());

		System.out.println("End " + getMethodName(new Object() {
		}));

		Long src = 11111L;
		ParserUtils.parse(src, NewNumberClass.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testParserFloatingHasChain() {
		IGenericParser<Number> parserNonFloating = new ParseToNonFloatingChain(null);
		IGenericParser parserFloating = new ParseToFloatingChain(parserNonFloating);

		Long src = 11111L;
		Long parsed = (Long) parserFloating.parse(src, Long.class);
		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testParserNonFloatingHasChain() {
		IGenericParser parserFloating = new ParseToFloatingChain(null);
		IGenericParser parserNonFloating = new ParseToNonFloatingChain(parserFloating);

		Double src = 11111D;
		Double parsed = (Double) parserNonFloating.parse(src, Double.class);
		Assert.assertEquals(src, parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testNoParserChainForNonFloating() {
		expectedException.expect(ParseException.class);
		expectedException.expectMessage("No wrapper found for target class : " + NewNumberClass.class);

		IGenericParser parser = new ParseToNonFloatingChain(null);

		System.out.println("End " + getMethodName(new Object() {
		}));

		Long src = 11111L;
		parser.parse(src, NewNumberClass.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testNoParserChainForFloating() {
		expectedException.expect(ParseException.class);
		expectedException.expectMessage("No wrapper found for target class : " + NewNumberClass.class);

		IGenericParser parser = new ParseToFloatingChain(null);

		System.out.println("End " + getMethodName(new Object() {
		}));

		Long src = 11111L;
		parser.parse(src, NewNumberClass.class);
	}

	@Test
	public void testParserToStringFromByteEquals() {
		Byte src = 111;
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromShortEquals() {
		Short src = 11111;
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromIntegerEquals() {
		Integer src = 11111;
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromLongEquals() {
		Long src = 11111L;
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromFloatEquals() {
		Float src = 1111.1F;
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromDoubleEquals() {
		Double src = 1111.1D;
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromBigIntegerEquals() {
		BigInteger src = new BigInteger("111111111111");
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromBigDecimalEquals() {
		BigDecimal src = new BigDecimal("111111111.111");
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromCharacterEquals() {
		Character src = 'B';
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromStringEquals() {
		String src = "1111111111111111111111111";
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromBooleanEquals() {
		Boolean src = true;
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromDateEquals() {
		Date src = new Date();
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromTimestampEquals() {
		Timestamp src = new Timestamp(System.currentTimeMillis());
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromSqlDateEquals() {
		java.sql.Date src = new java.sql.Date(System.currentTimeMillis());
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromUnknownClassEquals() {
		UnknownClass src = new UnknownClass("111111111");
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromNewDateClassEquals() {
		NewDateClass src = new NewDateClass();
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}

	@Test
	public void testParserToStringFromNewNumberClassEquals() {
		NewNumberClass src = new NewNumberClass();
		String parsed = ParserUtils.parse(src, String.class);

		Assert.assertEquals(src.toString(), parsed);

		System.out.println("End " + getMethodName(new Object() {
		}) + " : " + parsed);
	}
}