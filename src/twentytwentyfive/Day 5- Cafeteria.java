/**
 * @author: Ian
 * @date: 12/17/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class Cafeteria {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 5.txt"));
        part1(input);
        input = new Scanner(new File("2025 Input/day 5.txt"));
        part2(input);
    }

    private static void part1(Scanner file) {
        List<long[]> intervals = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            if (line.isEmpty()) {
                break;
            }

            intervals.add(new long[]{Long.parseLong(line.split("-")[0]),
                    Long.parseLong(line.split("-")[1])});
        }
        intervals.sort(Comparator.comparingLong(a -> a[0]));

        List<long[]> mergedIntervals = mergeIntervals(intervals);

        int fresh = 0;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            long id = Long.parseLong(line);

            int l = 0, r = mergedIntervals.size() - 1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                long start = mergedIntervals.get(mid)[0];
                long end = mergedIntervals.get(mid)[1];

                if (id < start) {
                    r = mid - 1;
                } else {
                    if (id <= end) {
                        fresh++;
                        break;
                    }
                    l = mid + 1;
                }
            }
        }

        System.out.println(fresh);
    }

    private static List<long[]> mergeIntervals(List<long[]> intervals) {
        List<long[]> result = new ArrayList<>();
        long[] cur = new long[]{intervals.getFirst()[0], intervals.getFirst()[1]};
        for (long[] interval : intervals) {
            if (interval[0] > cur[1] + 1) {
                result.add(cur);
                cur = new long[]{interval[0], interval[1]};
            } else {
                cur[1] = Math.max(cur[1], interval[1]);
            }
        }

        result.add(cur);
        return result;
    }

    private static void part2(Scanner file) {
        List<long[]> intervals = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            if (line.isEmpty()) {
                break;
            }

            intervals.add(new long[]{Long.parseLong(line.split("-")[0]),
                    Long.parseLong(line.split("-")[1])});
        }
        intervals.sort(Comparator.comparingLong(a -> a[0]));

        List<long[]> mergedIntervals = mergeIntervals(intervals);
        long fresh = 0;

        for (long[] interval : mergedIntervals) {
            fresh += interval[1] - interval[0] + 1;
        }

        System.out.println(fresh);
    }
}