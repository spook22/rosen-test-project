package rspasov.java8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Streams {

	public static void main(String[] args) {

		System.out.println("Enter text:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Stream<String> stream = in.lines().limit(2);

		ArrayList<String> input = new ArrayList<>();
		stream.forEach(t -> input.add(t));

		int n = Integer.parseInt(input.get(0));
		String[] arr = input.get(1).split(" ");
		for (; n - 1 >= 0; n--) {
			System.out.print(arr[n - 1] + " ");
		}
	}

}
