package org.rumusanframework.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationUtilsTest {
	private static final String SERIALIZE_TO_FILE = "SERIALIZE_TO_FILE";
	private static final String SERIALIZE_TO_STRING_FILE = "SERIALIZE_TO_STRING_FILE";
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<SerializationUtils> constructor = SerializationUtils.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testSuccessSerializeToFile() throws IOException {
		String string = "serialized to file.";
		String fileName = "/" + SerializationUtilsTest.class.getName() + SERIALIZE_TO_FILE;

		SerializationUtils.serializeToFile(fileName, string);
	}

	@Test
	public void testExceptionSerializeToFile() throws IOException {
		expectedException.expect(IOException.class);

		String string = "serialized to file.";
		String fileName = "";

		SerializationUtils.serializeToFile(fileName, string);
	}

	@Test
	public void testSuccessDeserializeFromFile() throws IOException {
		String string = "serialized to file.";
		String fileName = "/" + SerializationUtilsTest.class.getName() + SERIALIZE_TO_FILE;

		SerializationUtils.serializeToFile(fileName, string);
		String deserializedString = SerializationUtils.deserializeFromFile(fileName, string.getClass());

		Assert.assertNotNull(deserializedString);
		Assert.assertEquals(string, deserializedString);
	}

	@Test
	public void testExceptionDeserializeFromFile() throws IOException {
		expectedException.expect(IOException.class);
		SerializationUtils.deserializeFromFile(SerializationUtilsTest.class.getName() + new Date().toString(),
				String.class);
	}

	@Test
	public void testSuccessWriteToStringFile() throws IOException {
		String string = "serialized to file.";
		String fileName = "/" + SerializationUtilsTest.class.getName() + SERIALIZE_TO_STRING_FILE;
		ObjectMapper mapper = new ObjectMapper();

		SerializationUtils.writeToStringFile(fileName, string, mapper);
	}

	@Test
	public void testExceptionwriteToStringFile() throws IOException {
		expectedException.expect(IOException.class);
		ObjectMapper mapper = new ObjectMapper();

		String string = "serialized to file.";
		String fileName = "";

		SerializationUtils.writeToStringFile(fileName, string, mapper);
	}

	@Test
	public void testSuccessReadFromStringFile() throws IOException {
		String string = "serialized to file.";
		String fileName = "/" + SerializationUtilsTest.class.getName() + SERIALIZE_TO_STRING_FILE;
		ObjectMapper mapper = new ObjectMapper();

		SerializationUtils.writeToStringFile(fileName, string, mapper);
		String deserializedString = SerializationUtils.readFromStringFile(fileName, string.getClass(), mapper);

		Assert.assertNotNull(deserializedString);
		Assert.assertEquals(string, deserializedString);
	}

	@Test
	public void testExceptionReadFromStringFile() throws IOException {
		expectedException.expect(IOException.class);
		String fileName = SerializationUtilsTest.class.getName() + new Date().toString();
		ObjectMapper mapper = new ObjectMapper();

		SerializationUtils.readFromStringFile(fileName, String.class, mapper);
	}

	@Test
	public void testEmptyReadFromStringFile() throws IOException {
		String fileName = "/" + SerializationUtilsTest.class.getName() + SERIALIZE_TO_STRING_FILE;
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(fileName);
		boolean exist = file.exists();

		if (exist) {
			file.delete();
		}

		boolean created = file.createNewFile();

		Assert.assertTrue(created);

		String deserializedString = SerializationUtils.readFromStringFile(fileName, String.class, mapper);

		Assert.assertNull(deserializedString);
	}
}