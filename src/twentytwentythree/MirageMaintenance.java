/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class MirageMaintenance {
    private static final int DAY = 9;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            long sum_extrapolated = 0;
            while (s.hasNextLine()) {
                long sum_line = 0;
                String line = s.nextLine();
                long[] nums = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                int end_idx = nums.length - 1;
                while (!Arrays.stream(nums).allMatch(x -> x == 0)) {
                    for (int i = 0; i < end_idx; i++) {
                        nums[i] = nums[i + 1] - nums[i];
                    }

                    sum_line += nums[end_idx];
                    nums[end_idx] = 0;
                    --end_idx;
                    // System.out.println(Arrays.toString(nums));
                }

                // System.out.println(sum_line);
                sum_extrapolated += sum_line;
            }

            System.out.println(sum_extrapolated);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            long sum_extrapolated = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                long[] nums = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                long sum_line = nums[0];
                int end_idx = nums.length - 1;
                // System.out.println(Arrays.toString(nums));

                boolean add = false;
                while (!Arrays.stream(nums).allMatch(x -> x == 0)) {
                    for (int i = 0; i < end_idx; i++) {
                        nums[i] = nums[i + 1] - nums[i];
                    }

                    sum_line += add ? nums[0] : -nums[0];
                    nums[end_idx] = 0;
                    --end_idx;
                    // System.out.println(Arrays.toString(nums));

                    add = !add;
                }

                // System.out.println(sum_line);
                sum_extrapolated += sum_line;
            }

            System.out.println(sum_extrapolated);
        }
    }
}