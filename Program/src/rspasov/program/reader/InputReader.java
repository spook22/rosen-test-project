package rspasov.program.reader;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import rspasov.program.list.LinkedList;

/**
 * Program Assignment: Regression, Correlation, and Prediction Calculator
 * <p>
 * Description: This program is used for calculating the regression parameters,
 * correlation, and prediction used in PSP2.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class InputReader {

	private String pathname;
	private InputStream input;

	public InputReader(String pathname) {
		this.pathname = pathname;
	} // end

	public InputReader(InputStream input) {
		this.input = input;
	} // end

	public String getPathname() {
		return pathname;
	} // end

	public void setPathname(String pathname) {
		this.pathname = pathname;
	} // end

	public InputStream getInput() {
		return input;
	} // end

	public void setInput(InputStream input) {
		this.input = input;
	} // end

	public LinkedList read() throws Exception {
		if (input == null) {
			throw new RuntimeException("Input is null. Make sure you're using the right resource and it's in the classpath: " + pathname);
		}
		LinkedList list = new LinkedList();
		Scanner scanner;
		if (pathname != null) {
			scanner = new Scanner(new File(pathname));
		} else {
			scanner = new Scanner(input);
		}
		try {
			while (scanner.hasNextDouble()) {
				double data = scanner.nextDouble();
				list.add(data);
			}
		} finally {
			scanner.close();
		}
		return list;
	} // end

} // end
