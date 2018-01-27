package rspasov.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestClassB {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClassB");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClassB");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUpB");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDownB");
	}

	@Test
	public void testBA() throws Exception {
		System.out.println("testBA");
		Thread.sleep(10000);
	}

}
