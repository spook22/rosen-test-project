package com.softwareag;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Utils {

	private static final String fileName = "records.txt";

	public static void record(Measurement measurement) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			PrintWriter writer = new PrintWriter(fileWriter);
			try {
				writer.println(measurement.toString());
				writer.flush();
			} finally {
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
