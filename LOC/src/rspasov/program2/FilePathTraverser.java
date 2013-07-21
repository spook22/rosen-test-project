package rspasov.program2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rspasov.program2.model.Item;
import rspasov.program2.scan.DefaultEventHandler;
import rspasov.program2.scan.ItemScanner;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes,
 * and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class FilePathTraverser {

	private final List<Item> model = new ArrayList<Item>();

	public void traverse(String pathname) throws IOException {
		File path = new File(pathname);
		traverse(path);
		System.out.println(model);
	} // end

	public void traverse(File path) throws IOException {
		if (path.isFile() && path.getName().endsWith(".java")) {
			System.out.println("Processing file: " + path.getAbsolutePath());
			ItemScanner scanner = new ItemScanner(new DefaultEventHandler());
			FileReader reader = new FileReader(path);
			BufferedReader buffReader = new BufferedReader(reader);
			try {
				scanner.scan(buffReader, path.getAbsolutePath());
			} finally {
				buffReader.close();
			}
			model.addAll(scanner.getModel());
		} else if (path.isDirectory()) {
			for (File child : path.listFiles()) {
				traverse(child);
			}
		}
	} // end

	public List<Item> getModel() {
		return Collections.unmodifiableList(model);
	} // end

} // end
