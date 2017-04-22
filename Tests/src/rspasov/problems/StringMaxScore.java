package rspasov.problems;

import java.io.IOException;

public class StringMaxScore {

	static String calculateScore(String text, String prefix, String suffix) {
		String result = "";
		if (text == null) {
			return result;
		}
		for (int index = 0; index < text.length(); index++) {
			if (prefix != null && prefix.length() > 0 && text.charAt(index) == prefix.charAt(prefix.length() - 1)) {
				int preTextPatternScore = findPreTextPatternScore(text, prefix, index);
				String temp = processForSuffix(text, suffix, index, preTextPatternScore);
				result = compareStrings(temp, result);
			}
			if (suffix != null && suffix.length() > 0 && text.charAt(index) == suffix.charAt(0)) {
				int postTextPatternScore = findPostTextPatternScore(text, suffix, index);
				String temp = processForPrefix(text, prefix, index, postTextPatternScore);
				result = compareStrings(temp, result);
			}
		}
		return result;
	}

	private static String compareStrings(String first, String second) {
		if (first.length() == second.length()) {
			return first.compareTo(second) < 0 ? first : second;
		} else if (first.length() > second.length()) {
			return first;
		} else {
			return second;
		}
	}

	private static int findPostTextPatternScore(String text, String suffix, int index) {
		int score = 0;
		int suffixIndex = 0;
		while (suffixIndex < suffix.length() && index < text.length() && suffix.charAt(suffixIndex++) == text.charAt(index++)) {
			score++;
		}
		return score;
	}

	private static int findPreTextPatternScore(String text, String prefix, int index) {
		int score = 0;
		for (int prefixIndex = prefix.length() - 1; prefixIndex >= 0 && index >= 0; prefixIndex--, index--) {
			if (prefix.charAt(prefixIndex) == text.charAt(index)) {
				score++;
			} else {
				break;
			}
		}
		return score;
	}

	public static void main(String[] args) throws IOException {
		String text = "nothing";
		String prefix = "bruno";
		String suffix = "ingenious";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "nothingin";
		prefix = "bruno";
		suffix = "ingenious";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "ab";
		prefix = "b";
		suffix = "a";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "bruno";
		prefix = "bruno";
		suffix = "ingenious";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "ingenious";
		prefix = "bruno";
		suffix = "ingenious";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "ba";
		prefix = "b";
		suffix = "aa";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "ba";
		prefix = "c";
		suffix = "g";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "b";
		prefix = "c";
		suffix = "g";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "b";
		prefix = "b";
		suffix = "g";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "b";
		prefix = "a";
		suffix = "b";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "";
		prefix = "a";
		suffix = "b";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "b";
		prefix = "";
		suffix = "b";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "b";
		prefix = "a";
		suffix = "";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "b";
		prefix = null;
		suffix = "";
		System.out.println(calculateScore(text, prefix, suffix));

		text = "b";
		prefix = "a";
		suffix = null;
		System.out.println(calculateScore(text, prefix, suffix));

		text = null;
		prefix = "a";
		suffix = "";
		System.out.println(calculateScore(text, prefix, suffix));

		System.out.println(calculateScore(null, null, null));
	}

	private static String processForPrefix(String text, String prefix, int index, int suffixScore) {
		String result = text.substring(index, index + suffixScore);
		for (int currIndex = index; currIndex >= 0; currIndex--) {
			if (prefix != null && prefix.length() > 0 && text.charAt(currIndex) == prefix.charAt(prefix.length() - 1)) {
				int prefixScore = findPreTextPatternScore(text, prefix, currIndex);
				String substr = text.substring(currIndex - prefixScore + 1, index + suffixScore);
				result = compareStrings(substr, result);
			}
		}
		return result;
	}

	private static String processForSuffix(String text, String suffix, int index, int prefixScore) {
		String result = text.substring(index - prefixScore + 1, index + 1);
		for (int currIndex = index; currIndex < text.length(); currIndex++) {
			if (suffix != null && suffix.length() > 0 && text.charAt(currIndex) == suffix.charAt(0)) {
				int suffixScore = findPostTextPatternScore(text, suffix, currIndex);
				String substr = text.substring(index - prefixScore + 1, currIndex + suffixScore);
				result = compareStrings(substr, result);
			}
		}
		return result;
	}

}
