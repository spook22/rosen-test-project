package rspasov.junit;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.model.TestTimedOutException;

public class TestClassA {

	@ClassRule
	public static Timeout classTimeout = new Timeout(5, TimeUnit.SECONDS);

	@Rule
	public Timeout testTimeout = new Timeout(2, TimeUnit.SECONDS);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClassA");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClassA");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUpA");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDownA");
	}

	@Test
	public void testA() {
		System.out.println("testA");
	}

	@Test(timeout = 1000, expected = TestTimedOutException.class)
	public void testB() throws Exception {
		System.out.println("testB");
		Thread.sleep(1500);
	}

	@Test(expected = TestTimedOutException.class)
	public void testC() throws Exception {
		System.out.println("testC");
		Thread.sleep(2500);
	}

	@Test(expected = InterruptedException.class)
	public void testD() throws Exception {
		System.out.println("testD");
		Thread.sleep(2500);
	}

}
