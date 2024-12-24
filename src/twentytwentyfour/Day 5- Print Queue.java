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
class PrintQueue {
    private static final int DAY = 5;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            Map<Integer, Set<Integer>> rules = new HashMap<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.isBlank()) {
                    break;
                }
                String[] nums = line.split("\\|");
                if (!rules.containsKey(Integer.parseInt(nums[0]))) {
                    rules.put(Integer.parseInt(nums[0]), new HashSet<>());
                }
                rules.get(Integer.parseInt(nums[0])).add(Integer.parseInt(nums[1]));
            }
            int sum = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                int[] nums = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                if (valid(nums, rules)) {
                    sum += nums[nums.length / 2];
                }
            }
            System.out.println(sum);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            Map<Integer, Set<Integer>> rules = new HashMap<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.isBlank()) {
                    break;
                }
                String[] nums = line.split("\\|");
                if (!rules.containsKey(Integer.parseInt(nums[0]))) {
                    rules.put(Integer.parseInt(nums[0]), new HashSet<>());
                }
                rules.get(Integer.parseInt(nums[0])).add(Integer.parseInt(nums[1]));
            }
            int sum = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                int[] nums = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                if (!valid(nums, rules)) {
                    selectionSort(nums, rules);
                    sum += nums[nums.length / 2];
                }
            }
            System.out.println(sum);
        }
    }

    private static boolean valid(int[] nums, Map<Integer, Set<Integer>> rules) {
        Set<Integer> after = new HashSet<>(rules.getOrDefault(nums[nums.length - 1], new HashSet<>()));
        boolean valid = true;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (after.contains(nums[i])) {
                valid = false;
                break;
            }
            after.addAll(rules.get(nums[i]));
        }
        return valid;
    }

    private static void selectionSort(int[] nums, Map<Integer, Set<Integer>> rules) {
        for (int i = nums.length - 1; i >= 0; i--) {
            if (rules.containsKey(nums[i])) {
                int max = nums[i], maxIdx = i;
                for (int j = i - 1; j >= 0; j--) {
                    if (rules.getOrDefault(max, Set.of()).contains(nums[j])) {
                        max = nums[j];
                        maxIdx = j;
                    }
                }
                int tmp = nums[i];
                nums[i] = nums[maxIdx];
                nums[maxIdx] = tmp;
            }
        }
    }
}