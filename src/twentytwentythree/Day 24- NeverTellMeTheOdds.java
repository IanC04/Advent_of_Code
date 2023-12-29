/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

class NeverTellMeTheOdds {
    private static final int DAY = 24;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        // part2();
    }

    private record HailStone(String name, long x, long y, long z, long dx, long dy, long dz) {

        /**
         * Ignores z-axis per problem description
         *
         * @param other the other HailStone to check for intersecting paths
         * @return the intersection point if it exists, otherwise {-1, -1}
         */
        private long[] intersection(HailStone other) {
            if ((double) dy / dx == (double) other.dy / other.dx) {
                return new long[]{-1, -1};
            } else {
                // x + t * dx = other.x + s * other.dx
                // y + t * dy = other.y + s * other.dy
                long[] b = new long[]{other.x - x, other.y - y};
                double frac = (double) 1 / ((dx * -other.dy) - (-other.dx * dy));
                long[][] a_adj = new long[][]{{-other.dy, dx}, {other.dx, -dy}};
                double[][] a_inverse = new double[][]{{frac * a_adj[0][0], frac * a_adj[0][1]},
                        {frac * a_adj[1][0], frac * a_adj[1][1]}};
                double[] t_s = new double[]{a_inverse[0][0] * b[0] + a_inverse[0][1] * b[1],
                        a_inverse[1][0] * b[0] + a_inverse[1][1] * b[1]};
                if (Arrays.stream(t_s).anyMatch(x -> x < 0)) {
                    return new long[]{-1, -1};
                }
                long x = (long) (this.x + t_s[0] * dx);
                long y = (long) (this.y + t_s[0] * dy);
                System.out.println(this.name + "[" + x + "," + y + "]" + other.name);
                return new long[]{x, y};
            }
        }
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            List<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }

            List<HailStone> stones = new ArrayList<>();
            char name = 'A';
            for (String line : lines) {
                String[] split = line.split("[,@ ]+");
                long[] nums = Arrays.stream(split).mapToLong(Long::parseLong).toArray();
                stones.add(new HailStone(name + "", nums[0], nums[1], nums[2], nums[3], nums[4],
                        nums[5]));
                ++name;
            }

            getAllIntersections(stones);
        }
    }

    private static void getAllIntersections(List<HailStone> stones) {
        long total = 0;
        for (int i = 0; i < stones.size(); i++) {
            for (int j = i + 1; j < stones.size(); j++) {
                long[] intersection = stones.get(i).intersection(stones.get(j));
                if (Arrays.stream(intersection).allMatch(x -> x >= 200000000000000L && x <= 400000000000000L)) {
                    ++total;
                }
            }
        }

        System.out.println(total);
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                System.out.println(line);
            }
        }
    }
}