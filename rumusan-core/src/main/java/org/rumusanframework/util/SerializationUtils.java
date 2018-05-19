package org.rumusanframework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SerializationUtils {
	private SerializationUtils() {
		// hide
	}

	public static void serializeToFile(String fileName, Object obj) throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;

		try {// NOSONAR
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(obj);
		} finally {
			closeQuietly(fos, oos);
		}
	}

	public static <T> T deserializeFromFile(String fileName, Class<T> targetClass) throws IOException {
		ObjectInputStream oos = null;
		FileInputStream fis = null;

		try {// NOSONAR
			fis = new FileInputStream(fileName);
			oos = new ObjectInputStream(fis);
			Object obj = oos.readObject();

			return targetClass.cast(obj);
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			closeQuietly(oos, fis);
		}
	}

	public static void writeToStringFile(String fileName, Object obj, ObjectMapper mapper) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;
		File file = new File(fileName);

		try {// NOSONAR
			String content = mapper.writeValueAsString(obj);
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			bw.write(content);
		} finally {
			closeQuietly(bw, fw);
		}
	}

	public static <T> T readFromStringFile(String fileName, Class<T> valueType, ObjectMapper mapper)
			throws IOException {
		BufferedReader br = null;
		FileReader fr = null;

		try {// NOSONAR
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			StringBuilder buff = new StringBuilder();
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				buff.append(sCurrentLine);
			}

			if (buff.length() > 0) {
				return mapper.readValue(buff.toString(), valueType);
			} else {
				return null;
			}
		} finally {
			closeQuietly(fr, br);
		}
	}

	private static void closeQuietly(Closeable... inputStream) {
		for (Closeable is : inputStream) {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {// NOSONAR
			}
		}
	}
}