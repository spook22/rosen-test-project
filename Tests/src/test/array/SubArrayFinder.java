package test.array;

import java.util.Arrays;

public class SubArrayFinder {

	public static boolean isSubArray(int[] first, int[] second) {
		if (first.length < second.length || first == null || second == null) {
			return false;
		}
		int secondStart = second[0];
		for (int index = 0; index <= first.length - second.length; index++) {
			if (first[index] == secondStart) {
				int[] subArray = Arrays.copyOfRange(first, index, index + second.length);
				boolean equal = areEqual(subArray, second);
				if (equal) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean areEqual(int[] first, int[] second) {
		if (first.length == second.length) {
			for (int index = 0; index < first.length; index++) {
				if (first[index] != second[index]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
