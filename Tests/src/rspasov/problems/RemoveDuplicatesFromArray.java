package rspasov.problems;

import java.util.Arrays;

public class RemoveDuplicatesFromArray {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3, 2, 2, 4, 5, 5, 5, 3, 8, 7, 6, 6 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3, 3, 2, 4, 5, 9, 5, 3, 8, 7, 6, 6 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3, 3 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3, 3, 3, 3 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3, 2 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3, 2, 2 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 3, 2, 3 })));
		System.out.println(Arrays.toString(removeDuplicates(new int[] { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9 })));

	}

	private static int[] removeDuplicates(int[] array) {
		if (array.length == 1) {
			return array;
		} else if (array.length == 2 && array[0] == array[1]) {
			return Arrays.copyOfRange(array, 0, 1);
		} else {
			int nextAvailableIndex = -1;
			boolean isDuplicate = false;
			for (int current = 1; current < array.length; current++) {
				for (int index = 0; index < current; index++) {
					if (array[current] == array[index]) {
						isDuplicate = true;
						break;
					}
				}
				if (!isDuplicate) {
					if (nextAvailableIndex != -1) {
						array[nextAvailableIndex++] = array[current];
					}
				} else {
					if (nextAvailableIndex == -1) {
						nextAvailableIndex = current;
					}
				}
				isDuplicate = false;
			}
			if (nextAvailableIndex == -1) {
				nextAvailableIndex = array.length;
			}
			return Arrays.copyOfRange(array, 0, nextAvailableIndex);
		}
	}

}
