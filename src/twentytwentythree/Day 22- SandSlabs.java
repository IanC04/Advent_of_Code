/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class SandSlabs {
    private static final int DAY = 22;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        // part2();
    }

    private static class Brick {
        private final String name;

        private final int x1, x2, y1, y2;

        private int z1, z2;

        private Brick(String name, int x1, int x2, int y1, int y2, int z1, int z2) {
            this.name = name;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
        }

        private Brick copy() {
            return new Brick(name, x1, x2, y1, y2, z1, z2);
        }

        @Override
        public String toString() {
            return String.format("%s: [%d, %d, %d]x[%d, %d, %d]", name, x1, y1, z1, x2, y2, z2);
        }
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<Brick> bricks = new ArrayList<>();
            int index = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] coords = line.split("~");
                int[] first = Arrays.stream(coords[0].split(",")).mapToInt(Integer::parseInt).toArray();
                int[] second = Arrays.stream(coords[1].split(",")).mapToInt(Integer::parseInt).toArray();
                bricks.add(new Brick(Integer.toString(++index), first[0], second[0], first[1], second[1],
                        first[2],
                        second[2]));
            }
            System.out.println(bricks);
        }
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