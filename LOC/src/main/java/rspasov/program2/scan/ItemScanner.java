package rspasov.program2.scan;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rspasov.program2.model.Item;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes,
 * and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class ItemScanner {

	private static final Pattern CLASS_PATTERN = Pattern.compile("(public )*(abstract )*(static )*(final )*(class|enum|interface) (.*) \\{");

	private static final Pattern METHOD_PATTERN = Pattern
			.compile("(public|private|protected )*(abstract )*(static )*(final )*(.* )?((\\w+)\\(([\\w<>]+ \\w+)*(, [\\w<>]+ \\w+)*\\)) (throws (.*))*\\{");

	private static final Pattern PACKAGE_PATTER = Pattern.compile("package (.*);");

	private final EventHandler handler;

	public ItemScanner(EventHandler handler) {
		this.handler = handler;
	} // end

	public void scan(BufferedReader reader, String name) throws IOException {
		handler.startFile(name);
		String line;
		while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}

			Matcher packageMatcher = PACKAGE_PATTER.matcher(line);
			Matcher classMatcher = CLASS_PATTERN.matcher(line);
			Matcher methodMatcher = METHOD_PATTERN.matcher(line);
			String packageName = null;
			if (packageMatcher.matches()) {
				packageName = packageMatcher.group(1);
				handler.processLine(line);
			} else if (classMatcher.matches()) {
				String className = packageName != null ? packageName + "." + classMatcher.group(6) : classMatcher.group(6);
				// System.out.println("Class found: " + packageName + "." +
				// className);
				handler.startClass(className);
				handler.processLine(line);
			} else if (methodMatcher.matches()) {
				String methodName = methodMatcher.group(7);
				// System.out.println("Method found: " + methodName);
				handler.startMethod(methodName);
				handler.processLine(line);
			} else if (line.equals("} // end")) {
				handler.processLine(line);
				handler.end();
			} else {
				handler.processLine(line);
			}
		}
		handler.end();
	} // end

	public List<Item> getModel() {
		return handler.getModel();
	} // end

}
