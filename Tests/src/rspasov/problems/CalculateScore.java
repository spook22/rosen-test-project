package rspasov.problems;

import java.util.Stack;

public class CalculateScore {

	public static int totalScore(String[] blocks, int n) {
		Stack<Integer> scores = new Stack<>();
		String block;
		int totalScore = 0;
		int lastScoreMinusOne = 0;
		int lastScore = 0;
		int currScore = 0;
		for (int index = 0; index < n; index++) {
			block = blocks[index];
			try {
				currScore = Integer.parseInt(block);
				scores.push(currScore);
			} catch (NumberFormatException e) {
				switch (block) {
				case "X":
					currScore = 2 * scores.peek();
					scores.push(currScore);
					break;
				case "+":
					currScore = scores.peek() + lastScoreMinusOne;
					scores.push(currScore);
					break;
				case "Z":
					currScore = -scores.pop();
					break;
				default:
					throw new RuntimeException("Unexpected input received.");
				}
			}
			totalScore += currScore;
			lastScoreMinusOne = lastScore;
			lastScore = currScore;
			System.out.println(totalScore);
		}
		return totalScore;
	}

}
