package rspasov.program2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import rspasov.program2.model.Item;
import rspasov.program2.report.ModelProcessor;
import rspasov.program2.report.ModelReport;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes, and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class MainTest {

	@Test
	public void testProgram1() throws Exception {
		FilePathTraverser traverser = new FilePathTraverser();
		traverser.traverse("C:\\Users\\rospa\\workspace\\Program1\\src\\rspasov\\program1");
		assertNotNull(traverser.getModel());
		List<Item> model = traverser.getModel();
		assertEquals(7, model.size());
		ModelReport report = ModelProcessor.process(model);
		System.out.println(report);
		assertNotNull(report);
		assertEquals(202, report.getTotalLines());
		assertEquals(7, report.getFiles());
		assertEquals(7, report.getClasses());
		assertEquals(18, report.getMethods());
	} // end

	@Test
	public void testProgram2() throws Exception {
		FilePathTraverser traverser = new FilePathTraverser();
		traverser.traverse("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2");
		assertNotNull(traverser.getModel());
		List<Item> model = traverser.getModel();
		assertEquals(12, model.size());
		ModelReport report = ModelProcessor.process(model);
		System.out.println(report);
		assertNotNull(report);
		assertEquals(503, report.getTotalLines());
		assertEquals(12, report.getFiles());
		assertEquals(12, report.getClasses());
		assertEquals(38, report.getMethods());
	} // end

	@Test
	public void testProgram3() throws Exception {
		FilePathTraverser traverser = new FilePathTraverser();
		traverser.traverse("C:\\Users\\rospa\\workspace\\Program3\\src\\rspasov\\program3");
		assertNotNull(traverser.getModel());
		List<Item> model = traverser.getModel();
		assertEquals(7, model.size());
		ModelReport report = ModelProcessor.process(model);
		System.out.println(report);
		assertNotNull(report);
		assertEquals(447, report.getTotalLines());
		assertEquals(7, report.getFiles());
		assertEquals(7, report.getClasses());
		assertEquals(34, report.getMethods());
	} // end

	@Test
	public void testProgram4() throws Exception {
		FilePathTraverser traverser = new FilePathTraverser();
		traverser.traverse("C:\\Users\\rospa\\workspace\\Program4\\src\\rspasov\\program4");
		assertNotNull(traverser.getModel());
		List<Item> model = traverser.getModel();
		assertEquals(7, model.size());
		ModelReport report = ModelProcessor.process(model);
		System.out.println(report);
		assertNotNull(report);
		assertEquals(537, report.getTotalLines());
		assertEquals(7, report.getFiles());
		assertEquals(7, report.getClasses());
		assertEquals(47, report.getMethods());
	} // end

	@Test
	public void testProgram() throws Exception {
		FilePathTraverser traverser = new FilePathTraverser();
		traverser.traverse("C:/Users/rospa/workspace/svn/Program");
		assertNotNull(traverser.getModel());
		List<Item> model = traverser.getModel();
		assertEquals(11, model.size());
		ModelReport report = ModelProcessor.process(model);
		System.out.println(report);
		assertNotNull(report);
		assertEquals(808, report.getTotalLines());
		assertEquals(11, report.getFiles());
		assertEquals(11, report.getClasses());
		assertEquals(86, report.getMethods());
	} // end

	@Test
	public void testProgramShared() throws Exception {
		FilePathTraverser traverser = new FilePathTraverser();
		traverser.traverse("C:\\Development\\workspaces\\vacations\\Google Code Trunk\\Program");
		assertNotNull(traverser.getModel());
		List<Item> model = traverser.getModel();
		assertEquals(9, model.size());
		ModelReport report = ModelProcessor.process(model);
		System.out.println(report);
		assertNotNull(report);
		assertEquals(808, report.getTotalLines());
		assertEquals(9, report.getFiles());
		assertEquals(9, report.getClasses());
		assertEquals(79, report.getMethods());
	} // end

} // end
