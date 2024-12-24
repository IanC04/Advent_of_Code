/**
 * @author: Ian Chen
 * @date: 12/24/2024
 */

package twentytwentyfour;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class RedNosedReports {
    private static final int DAY = 2;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            int safe = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                List<Integer> levels =
                        Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

                if (safe(levels)) {
                    safe++;
                }
            }
            System.out.println(safe);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            int safe = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                List<Integer> levels =
                        Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

                boolean found = safe(levels);

                for (int i = 0; i < levels.size(); i++) {
                    if (found) {
                        break;
                    }
                    int c = levels.get(i);
                    levels.remove(i);
                    found = safe(levels);
                    levels.add(i, c);
                }
                if (found) {
                    safe++;
                }
            }
            System.out.println(safe);
        }
    }

    private static boolean safe(List<Integer> levels) {
        List<Integer> diffs = new ArrayList<>(levels.size() - 1);
        for (int i = 0; i < levels.size() - 1; i++) {
            diffs.add(levels.get(i + 1) - levels.get(i));
        }

        return diffs.stream().allMatch(diff -> diff >= 1 && diff <= 3) || diffs.stream().allMatch(diff -> diff >= -3 && diff <= -1);
    }
}