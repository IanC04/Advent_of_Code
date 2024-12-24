/**
 * @author: Ian Chen
 * @date: 12/28/2024
 */

package twentytwentyfour;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class BridgeRepair {
    private static final int DAY = 7;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            long sum = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                long result = Long.parseLong(line.substring(0, line.indexOf(':')));
                long[] nums = Arrays.stream(line.substring(line.indexOf(' ') + 1).split(" ")).mapToLong(Long::parseLong).toArray();
                boolean equivalent = backtrack(result, nums, 0, 0);
                if (equivalent) {
                    sum += result;
                }
            }
            System.out.println(sum);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            long sum = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                long result = Long.parseLong(line.substring(0, line.indexOf(':')));
                long[] nums = Arrays.stream(line.substring(line.indexOf(' ') + 1).split(" ")).mapToLong(Long::parseLong).toArray();
                boolean equivalent = backtrack2(result, nums, 0, 0);
                if (equivalent) {
                    sum += result;
                }
            }
            System.out.println(sum);
        }
    }

    private static boolean backtrack(long result, long[] nums, int pos, long value) {
        if (pos == nums.length) {
            return value == result;
        }
        return backtrack(result, nums, pos + 1, value + nums[pos]) || backtrack(result, nums, pos + 1, value * nums[pos]);
    }

    private static boolean backtrack2(long result, long[] nums, int pos, long value) {
        if (pos == nums.length) {
            return value == result;
        }
        return backtrack2(result, nums, pos + 1, value + nums[pos])
                || backtrack2(result, nums, pos + 1, value * nums[pos])
                || backtrack2(result, nums, pos + 1, Long.parseLong(value + "" + nums[pos]));
    }
}