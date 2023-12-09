/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */
package twentytwentythree;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class WaitForIt {
    private static final int DAY = 6;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            while (s.hasNextLine()) {
                String line = s.nextLine().replace("Time: ", "").trim();
                String[] s_times = line.split(" +");
                // System.out.println(Arrays.toString(s_times));
                int[] times = Arrays.stream(s_times).mapToInt(Integer::parseInt).toArray();

                line = s.nextLine().replace("Distance: ", "").trim();
                String[] s_distances = line.split(" +");
                // System.out.println(Arrays.toString(s_distances));
                int[] distances = Arrays.stream(s_distances).mapToInt(Integer::parseInt).toArray();

                assert times.length == distances.length;
                int[] possible_wins = new int[times.length];
                for (int i = 0; i < times.length; i++) {
                    int possible = win_combos(times[i], distances[i]);
                    possible_wins[i] = possible;
                    System.out.println(possible);
                }

                System.out.println(Arrays.stream(possible_wins).mapToObj(BigInteger::valueOf).reduce(BigInteger.ONE, BigInteger::multiply));
            }
        }
    }

    private static int win_combos(int time, int distance) {
        int wins = 0;
        for (int speed = 1; speed < time; ++speed) {
            if (speed * (time - speed) > distance) {
                ++wins;
            }
        }
        return wins;
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            while (s.hasNextLine()) {
                String line = s.nextLine().replace("Time: ", "").trim();
                String s_time = line.replace(" ", "");
                // System.out.println(Arrays.toString(s_time));
                long time = Long.parseLong(s_time);

                line = s.nextLine().replace("Distance: ", "").trim();
                String s_distance = line.replace(" ", "");
                // System.out.println(Arrays.toString(s_distance));
                long distance = Long.parseLong(s_distance);

                long possible_wins = win_combos_2(time, distance);
                System.out.println(possible_wins);
            }
        }
    }

    private static long win_combos_2(long time, long distance) {
        long min_hold, max_hold;

        long left = 0, right = time, mid;
        while (left < right) {
            mid = (left + right) / 2;
            if (mid * (time - mid) > distance) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        min_hold = left;

        left = 0;
        right = time;
        while (left < right) {
            mid = (left + right) / 2;
            if (mid * (time - mid) <= distance) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        max_hold = left;

        return max_hold - min_hold;
    }
}