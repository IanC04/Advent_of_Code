/*
   Written by Ian Chen
   GitHub: https://github.com/IanC04
*/
package twentytwentythree;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

class Scratchcards {
    private static final int DAY = 4;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            int score = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] split = line.substring(line.indexOf(":") + 2).split(" \\| ");
                // System.out.println(split[0]);
                // System.out.println(split[1]);
                String[] numbers = split[0].trim().split(" +");
                HashSet<Integer> set = new HashSet<>(Arrays.stream(numbers).map(Integer::parseInt).toList());
                int[] scratch =
                        Arrays.stream(split[1].trim().split(" +")).mapToInt(Integer::parseInt).toArray();
                int matches = 0;
                for (int i : scratch) {
                    if (set.contains(i)) {
                        ++matches;
                    }
                }

                score += (int) Math.pow(2, matches - 1);
                // System.out.println(score);
            }

            System.out.println(score);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            List<Integer> scratch_count = new ArrayList<>();
            int index = 0;
            while (s.hasNextLine()) {
                if (scratch_count.size() == index) {
                    scratch_count.add(0);
                }
                scratch_count.set(index, scratch_count.get(index) + 1);

                String line = s.nextLine();
                String[] split = line.substring(line.indexOf(":") + 2).split(" \\| ");
                String[] numbers = split[0].trim().split(" +");
                HashSet<Integer> set = new HashSet<>(Arrays.stream(numbers).map(Integer::parseInt).toList());
                int[] scratch =
                        Arrays.stream(split[1].trim().split(" +")).mapToInt(Integer::parseInt).toArray();
                int matches = 0;
                for (int i : scratch) {
                    if (set.contains(i)) {
                        ++matches;
                    }
                }

                for (int i = 0; i < matches; i++) {
                    if (scratch_count.size() == index + i + 1) {
                        scratch_count.add(0);
                    }
                    scratch_count.set(index + i + 1, scratch_count.get(index + i + 1) + scratch_count.get(index));
                }

                ++index;
            }

            System.out.println(scratch_count.stream().mapToInt(Integer::intValue).sum());
        }
    }
}