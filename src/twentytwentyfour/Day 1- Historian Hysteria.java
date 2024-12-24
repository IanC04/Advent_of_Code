/**
 * @author: Ian Chen
 * @date: 12/24/2024
 */

package twentytwentyfour;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class HistorianHysteria {
    private static final int DAY = 1;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            List<Integer> left = new ArrayList<>();
            List<Integer> right = new ArrayList<>();
            while (s.hasNextInt()) {
                left.add(s.nextInt());
                right.add(s.nextInt());
                left.sort(Integer::compareTo);
                right.sort(Integer::compareTo);
            }

            int diff = 0;
            for (int i = 0; i < left.size(); i++) {
                diff += Math.abs(left.get(i) - right.get(i));
            }

            System.out.println(diff);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            Map<Integer, Integer> left = new HashMap<>();
            Map<Integer, Integer> right = new HashMap<>();

            while (s.hasNextInt()) {
                int l = s.nextInt();
                int r = s.nextInt();

                left.put(l, left.getOrDefault(l, 0) + 1);
                right.put(r, right.getOrDefault(r, 0) + 1);
            }

            int sim = 0;
            for (int k : left.keySet()) {
                sim += k * right.getOrDefault(k, 0);
            }

            System.out.println(sim);
        }
    }
}