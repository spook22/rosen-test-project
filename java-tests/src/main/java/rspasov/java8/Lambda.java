package rspasov.java8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Lambda {

    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static List<Person> persons =
            Arrays.asList(
                    new Person("Max", 18),
                    new Person("Peter", 23),
                    new Person("Pamela", 23),
                    new Person("David", 12));

    public static void main(String[] args) {
        readInput();

        persons.stream();
    }

    private static void readInput() {

        final Spliterator<String> spliterator = new BufferedReader(new InputStreamReader(System.in)).lines().spliterator();

        Spliterator<String> inputSpliterator = new Spliterator<String>() {
            private boolean firstLine = true;

            private int lines;

            @Override
            public boolean tryAdvance(Consumer<? super String> action) {
                if (firstLine) {
                    firstLine = false;
                    System.out.print("Enter number of commands: ");
                    return spliterator.tryAdvance(input -> lines = Integer.parseInt(input));
                } else {
                    for (int i = 0; i < lines; i++) {
                        System.out.print("Enter command: ");
                        spliterator.tryAdvance(input -> readCommand(input));
                    }
                    return false;
                }
            }

            private void readCommand(String command) {
                String[] commandInput = command.split(" ");
                switch (commandInput[0]) {
                    case "1":
                        boolean even = Integer.parseInt(commandInput[1]) % 2 == 0;
                        System.out.println(even ? "EVEN" : "UNEVEN");
                        break;
                }
            }

            @Override
            public Spliterator<String> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                if (firstLine) {
                    return spliterator.estimateSize();
                } else {
                    return lines;
                }
            }

            @Override
            public int characteristics() {
                return spliterator.characteristics();
            }
        };

        while (inputSpliterator.tryAdvance(System.out::println));
    }
}
