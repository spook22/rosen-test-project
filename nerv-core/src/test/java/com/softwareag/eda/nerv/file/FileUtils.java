package com.softwareag.eda.nerv.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileUtils {

	public static final String ENCODING = "UTF-8";

	public static String read(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead = input.read(buffer);
		while (bytesRead != -1) {
			output.write(buffer, 0, bytesRead);
			bytesRead = input.read(buffer);
		}
		return output.toString(ENCODING);
	}

	public static String readFile(File file) throws IOException {
		byte[] buffer = new byte[(int) file.length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(file));
			f.read(buffer);
		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
			}
		}
		return new String(buffer, ENCODING);
	}

	public static void writeFile(File file, String data) throws IOException {
		BufferedOutputStream f = null;
		try {
			if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
				throw new IOException("Unable to create directories for file:" + file.getPath());
			}
			// Don't use FileWriter because it relies on default encoding.
			f = new BufferedOutputStream(new FileOutputStream(file));
			f.write(data.getBytes(ENCODING));
		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
			}
		}
	}

}
