/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.stream.IntStream;

class HotSprings {
    private static final int DAY = 12;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            long total = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                char[] data = line.substring(0, line.indexOf(' ')).toCharArray();
                int[] criteria =
                        Arrays.stream(line.substring(line.indexOf(' ') + 1).split(",")).mapToInt(Integer::parseInt).toArray();
                // System.out.println(Arrays.toString(data) + " " + Arrays.toString(criteria));

                long arrangements = getCombinations(data, criteria);
                // System.out.println(arrangements);
                total += arrangements;
            }

            System.out.println(total);
        }
    }

    private static long getCombinations(char[] data, int[] criteria) {
        return getCombinationsHelper(data, criteria, 0);
    }

    private static long getCombinationsHelper(char[] data, int[] criteria, int position) {
        if (position == data.length) {
            String[] groups = String.valueOf(data).replaceAll("\\.+", " ").trim().split(" ");
            // Return if each group has same length specified by criteria
            return groups.length == criteria.length && IntStream.range(0, groups.length).allMatch(i -> groups[i].length() == criteria[i]) ? 1 : 0;
        }

        long total = 0;
        if (data[position] == '?') {
            data[position] = '.';
            total += getCombinationsHelper(data, criteria, position + 1);
            data[position] = '#';
            total += getCombinationsHelper(data, criteria, position + 1);
            data[position] = '?';
        } else {
            total += getCombinationsHelper(data, criteria, position + 1);
        }

        return total;
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            long total = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String data_string = (line.substring(0, line.indexOf(' ')) + '?').repeat(5);
                char[] data = data_string.substring(0, data_string.length() - 1).toCharArray();
                int[] criteria =
                        Arrays.stream((line.substring(line.indexOf(' ') + 1) + ',').repeat(5).split(
                                ",")).mapToInt(Integer::parseInt).toArray();

                long arrangements = getCombinations2(data, criteria);
                // System.out.println(arrangements);
                total += arrangements;
            }

            System.out.println(total);
        }
    }

    private static long getCombinations2(char[] data, int[] criteria) {
        HashMap<String, Long> memo = new HashMap<>();
        return getCombinationsHelper2(data, criteria, memo, 0, 0);
    }

    private static long getCombinationsHelper2(char[] data, int[] criteria,
                                               HashMap<String, Long> memo, int position,
                                               int groupNumber) {
        if (position == data.length) {
            String[] groups = String.valueOf(data).replaceAll("\\.+", " ").trim().split(" ");
            // Return if each group has the same length specified by criteria
            return groups.length == criteria.length && IntStream.range(0, groups.length).allMatch(i -> groups[i].length() == criteria[i]) ? 1 : 0;
        }
        String key = String.valueOf(data).substring(position);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        long total = 0;
        if (data[position] == '?') {
            data[position] = '.';
            long dotStart = getCombinationsHelper2(data, criteria, memo, position + 1, 0);
            assert !memo.containsKey(key);
            memo.put(key, dotStart);
            total += dotStart;

            data[position] = '#';
            long hashStart = getCombinationsHelper2(data, criteria, memo, position + 1, 0);
            assert !memo.containsKey(key);
            memo.put(key, hashStart);
            total += hashStart;
            data[position] = '?';
        } else {
            long knownStart = getCombinationsHelper2(data, criteria, memo, position + 1, 0);
            assert !memo.containsKey(key);
            memo.put(key, knownStart);
            total += knownStart;
        }

        return total;
    }

    private static class State {
        String data;
        int groupNumber;

        public State(String data, int[] criteria, int position, int groupNumber) {
            this.data = data;
            this.groupNumber = groupNumber;
        }
    }
}