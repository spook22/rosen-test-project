package rspasov.problems;

public class MagicOf3 {

	public static void main(String[] args) {
		findSequence(3);
		findSequence(13);
		findSequence(23);
		findSequence(183);
		findSequence(1333);
		findSequence(136633);
		findSequence(1392038622221321313l);
	}
	
	private static void printSequence(int sequenceLength) {
		for (int i = 0; i < sequenceLength; i++) {
			System.out.print(1);
		}
		System.out.println();
	}
	
	private static void findSequence(long number) {
		int sequenceLength = 0;
		long remainder = 0;
		do {
			sequenceLength++;
			remainder = (remainder * 10 + 1) % number;
		} while (remainder != 0);
		printSequence(sequenceLength);
	}

}
