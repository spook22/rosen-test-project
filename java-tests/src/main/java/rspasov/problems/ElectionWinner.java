package rspasov.problems;

import java.util.HashMap;

public class ElectionWinner {

	static String electionWinner(String[] votes) {
		int numberOfVotes = Integer.parseInt(votes[0]);
		HashMap<String, Integer> votesPerPerson = new HashMap<>();

		for (int index = 1; index <= numberOfVotes; index++) {
			Integer votesForThisPerson = votesPerPerson.get(votes[index]);
			if (votesForThisPerson == null) {
				votesForThisPerson = 0;
			}
			votesPerPerson.put(votes[index], ++votesForThisPerson);
		}

		java.util.Map.Entry<String, Integer> winner = null;
		for (java.util.Map.Entry<String, Integer> entry : votesPerPerson.entrySet()) {
			if (winner == null) {
				winner = entry;
			} else {
				if (winner.getValue().intValue() < entry.getValue().intValue()) {
					winner = entry;
				} else if (winner.getValue().intValue() == entry.getValue().intValue()) {
					winner = (winner.getKey().compareTo(entry.getKey()) > 0) ? winner : entry;
				}
			}
		}

		System.out.println(votesPerPerson);
		System.out.println(winner.getKey());
		return winner.getKey();

	}

	public static void main(String[] args) {
		electionWinner(new String[] { "10", "Alex", "Michael", "Harry", "Dave", "Michael", "Victor", "Harry", "Alex", "Mary", "Mary" });
		electionWinner(new String[] { "10", "Victor", "Veronica", "Ryan", "Dave", "Maria", "Maria", "Farah", "Farah", "Ryan", "Veronica" });

	}

}
