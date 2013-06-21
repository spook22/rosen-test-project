package com.softwareag.tools.vacation.planner.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TestUtil {

	public static String readResource(String name) throws IOException {

		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader) cl).getURLs();
		for (URL url : urls) {
			System.out.println(url.getFile());
		}

		InputStream input = ClassLoader.getSystemResourceAsStream(name);
		if (input == null) {
			throw new RuntimeException("Resource " + name + " cannot be found.");
		}
		int BUFF_SIZE = 1024;
		byte[] buffer = new byte[BUFF_SIZE];
		int bytesRead = -1;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} finally {
			input.close();
		}
		return output.toString("UTF-8");
	}

	public static void createSchema(EntityManager manager) {
		try {
			String sqlString = readResource("import.sql");
			Query query = manager.createNativeQuery(sqlString);
			try {
				manager.getTransaction().begin();
				query.executeUpdate();
			} finally {
				manager.getTransaction().rollback();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
