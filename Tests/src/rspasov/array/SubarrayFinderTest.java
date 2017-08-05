package rspasov.array;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SubarrayFinderTest {

	@Test
	public void testIsSubArray() {
		int size = 100000000;
		int[] first = new int[size];
		int[] second = new int[size / 2];
		for (int i = 0; i < size; i++) {
			first[i] = i;
			if (i >= (size / 2)) {
				second[i - (size / 2)] = i;
			}
		}
		// System.out.println(Arrays.toString(first));
		// System.out.println(Arrays.toString(second));
		for (int i = 0; i < 100; i++) {
			assertTrue(SubArrayFinder.isSubArray(first, second));
		}
	}

}
