package rspasov.program2;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Program Assignment: LOC Counting Program
 * <p>
 * Description: This program is used for counting source code lines, classes,
 * and methods. It's used by the PSP to track and estimate programs' sizes.
 * 
 * @author Rosen Spasov
 * @version 1.0
 */
public class FilePathTraverserTest {

	@Test
	public void testTraverseString() throws Exception {
		FilePathTraverser traverser = new FilePathTraverser();
		traverser.traverse("C:\\Users\\rospa\\workspace\\Program2\\src\\rspasov\\program2");
		assertNotNull(traverser.getModel());
	} // end
} // end
