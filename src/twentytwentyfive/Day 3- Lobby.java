/**
 * @author: Ian
 * @date: 12/16/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class Lobby {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 3.txt"));
        part1(input);
        input = new Scanner(new File("2025 Input/day 3.txt"));
        part2(input);
    }

    private static void part1(Scanner file) {
        int sum = 0;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            int[] bank = Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray();
            int maxTens = -1, max = Integer.MIN_VALUE;
            for (int i : bank) {
                max = Math.max(max, maxTens * 10 + i);
                maxTens = Math.max(maxTens, i);
            }
            sum += max;
        }

        System.out.println(sum);
    }

    private static void part2(Scanner file) {
        long sum = 0;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            int[] bank = Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray();
            long max = dp(bank, 12);
            sum += max;
        }

        System.out.println(sum);
    }

    private static long dp(int[] bank, int count) {
        long[] exp = new long[count + 1];
        exp[1] = 1;
        for (int i = 2; i < exp.length; i++) {
            exp[i] = exp[i - 1] * 10;
        }
        long[] dp = new long[count + 1];
        for (int i = bank.length - 1; i >= 0; i--) {
            for (int j = Math.min(bank.length + 1 - i, dp.length) - 1; j > 0; j--) {
                dp[j] = Math.max(dp[j], bank[i] * exp[j] + dp[j - 1]);
            }
        }
        return dp[count];
    }
}