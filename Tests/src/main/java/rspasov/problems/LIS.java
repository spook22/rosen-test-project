package rspasov.problems;

public class LIS {

	static int findLIS(int[] s) {
		int maxSequenceLength = 1;
		for (int i = 0; i < s.length; i++) {
			int prevElement = s[i];
			int currentSequenceLength = 1;
			for (int j = i + 1; j < s.length; j++) {
				int currentElement = s[j];
				if (prevElement < currentElement) {
					currentSequenceLength++;
					prevElement = currentElement;
				}
			}
			if (maxSequenceLength < currentSequenceLength) {
				maxSequenceLength = currentSequenceLength;
			}
		}
		return maxSequenceLength;
	}

	public static void main(String[] args) {
		System.out.println(findLIS(new int[] { 1, 4, 3 }));
		System.out.println(findLIS(new int[] { 1, 4, 5, 2, 6 }));
		System.out.println(findLIS(new int[] { 2, 3, 3, 5 }));
		System.out.println(findLIS(new int[] { 2, 1, 2, 3 }));
		System.out.println(findLIS(new int[] { 5, 4, 3, 2, 1 }));
		System.out.println(findLIS(new int[] { 1, 2, 3, 4, 5 }));
		System.out.println(findLIS(new int[] { 1 }));
		System.out.println(findLIS(new int[0]));
		System.out.println(findLIS(new int[] { -1, 2, 5, -10, 11, 12, 13 }));
		System.out.println(findLIS(new int[] { 2, 1, 3, 10, 5 }));
		System.out.println(findLIS(new int[] { 1, 1, 1, 1 }));
		System.out.println(findLIS(new int[] { 1, 1, 1, 99 }));
		System.out.println(findLIS(new int[] { 1, 1, 1, 1000000 }));

	}

}
