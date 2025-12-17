/**
 * @author: Ian
 * @date: 12/18/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class MovieTheater {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 9.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 9.txt"));
        partTwo(input);
    }

    private record Point(long x, long y) {
    }

    private static void partOne(Scanner file) {
        List<Point> red = new ArrayList<>();
        while (file.hasNextLine()) {
            long[] line = Arrays.stream(file.nextLine().split(","))
                    .mapToLong(Long::parseLong)
                    .toArray();
            red.add(new Point(line[0], line[1]));
        }

        long max = 0;
        for (int i = 0; i < red.size(); i++) {
            Point left = red.get(i);
            for (int j = i + 1; j < red.size(); j++) {
                Point right = red.get(j);

                max = Math.max(max, area(left, right));
            }
        }

        System.out.println(max);
    }

    private static long area(Point p1, Point p2) {
        long width = Math.abs(p2.x - p1.x) + 1;
        long height = Math.abs(p2.y - p1.y) + 1;
        return width * height;
    }

    private static void partTwo(Scanner file) {
        List<Point> red = new ArrayList<>();

        while (file.hasNextLine()) {
            long[] line = Arrays.stream(file.nextLine().split(","))
                    .mapToLong(Long::parseLong)
                    .toArray();
            red.add(new Point(line[0], line[1]));
        }

        long max = 0;
        for (int i = 0; i < red.size(); i++) {
            Point left = red.get(i);
            for (int j = i + 1; j < red.size(); j++) {
                Point right = red.get(j);

                if (!intersect(red, left, right)) {
                    max = Math.max(max, area(left, right));
                }
            }
        }

        System.out.println(max);
    }

    private static boolean intersect(List<Point> red, Point left, Point right) {
        for (int i = 0; i < red.size(); i++) {
            Point p = red.get(i);
            Point b = red.get((i + 1) % red.size());
            if (p.equals(left) || p.equals(right)
                    || b.equals(left) || b.equals(right)) {
                continue;
            }

            Point mid = new Point((p.x + b.x) / 2, (p.y + b.y) / 2);

            if (Math.min(left.x, right.x) < mid.x && mid.x < Math.max(left.x, right.x)
                    && Math.min(left.y, right.y) < mid.y && mid.y < Math.max(left.y, right.y)) {
                return true;
            }
        }

        return false;
    }
}