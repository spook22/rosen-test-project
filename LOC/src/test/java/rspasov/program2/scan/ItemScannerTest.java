package rspasov.program2.scan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.junit.Test;

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
public class ItemScannerTest {

	private List<Item> testScan(File path) throws Exception {
		FileReader reader = new FileReader(path);
		BufferedReader buffReader = new BufferedReader(reader);
		ItemScanner scanner = new ItemScanner(new DefaultEventHandler());
		try {
			scanner.scan(buffReader, path.getAbsolutePath());
		} finally {
			buffReader.close();
		}
		System.out.println(scanner.getModel());
		assertNotNull(scanner.getModel());
		return scanner.getModel();
	}

	@Test
	public void testScanItem() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\model\\Item.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(58, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(54, model.get(0).getChildren().get(0).getLOC());
		assertEquals(11, model.get(0).getChildren().get(0).getChildren().size());
		assertEquals(4, model.get(0).getChildren().get(0).getChildren().get(0).getLOC());
	}

	@Test
	public void testScanItemType() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\model\\ItemType.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(6, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(5, model.get(0).getChildren().get(0).getLOC());
	}

	@Test
	public void testScanModelProcessor() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\report\\ModelProcessor.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(28, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(25, model.get(0).getChildren().get(0).getLOC());
		assertEquals(2, model.get(0).getChildren().get(0).getChildren().size());
		assertEquals(5, model.get(0).getChildren().get(0).getChildren().get(0).getLOC());
	}

	@Test
	public void testScanFileDefaultEventHandler() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\scan\\DefaultEventHandler.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(57, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(51, model.get(0).getChildren().get(0).getLOC());
		assertEquals(7, model.get(0).getChildren().get(0).getChildren().size());
		assertEquals(9, model.get(0).getChildren().get(0).getChildren().get(5).getLOC());
	}

	@Test
	public void testScanFileEventHandler() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\scan\\EventHandler.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(12, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(9, model.get(0).getChildren().get(0).getLOC());
		assertEquals(0, model.get(0).getChildren().get(0).getChildren().size());
	}

	@Test
	public void testScanItemScanner() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\scan\\ItemScanner.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(52, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(45, model.get(0).getChildren().get(0).getLOC());
		assertEquals(3, model.get(0).getChildren().get(0).getChildren().size());
		assertEquals(32, model.get(0).getChildren().get(0).getChildren().get(1).getLOC());
	}

	@Test
	public void testScanFilePathTraverserTest() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\FilePathTraverserTest.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(11, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(8, model.get(0).getChildren().get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().get(0).getChildren().size());
		assertEquals(5, model.get(0).getChildren().get(0).getChildren().get(0).getLOC());
	}

	@Test
	public void testScanFilePathTraverser() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\FilePathTraverser.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(40, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(29, model.get(0).getChildren().get(0).getLOC());
		assertEquals(3, model.get(0).getChildren().get(0).getChildren().size());
		assertEquals(5, model.get(0).getChildren().get(0).getChildren().get(0).getLOC());
	}

	@Test
	public void testScanMainTest() throws Exception {
		File path = new File("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2\\MainTest.java");
		List<Item> model = testScan(path);
		assertEquals(1, model.size());
		assertEquals(24, model.get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().size());
		assertEquals(16, model.get(0).getChildren().get(0).getLOC());
		assertEquals(1, model.get(0).getChildren().get(0).getChildren().size());
		assertEquals(13, model.get(0).getChildren().get(0).getChildren().get(0).getLOC());
	}
}
