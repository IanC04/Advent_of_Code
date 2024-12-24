/**
 * @author: Ian Chen
 * @date: 12/24/2024
 */

package twentytwentyfour;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class MullItOver {
    private static final int DAY = 3;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            int result = s.findAll("mul\\(\\d+,\\d+\\)").mapToInt(match -> {
                String[] nums = match.group().substring(4, match.group().length() - 1).split(",");
                return Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]);
            }).reduce(Integer::sum).getAsInt();
            System.out.println(result);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            List<String> tokens =
                    s.findAll("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))").map(MatchResult::group).toList();

            int result = 0;
            boolean exclude = false;
            for (String token : tokens) {
                if (token.startsWith("mul")) {
                    if (exclude) {
                        continue;
                    }
                    String[] nums = token.substring(4, token.length() - 1).split(",");
                    result += Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]);
                } else exclude = token.startsWith("don't");
            }
            System.out.println(result);
        }
    }
}