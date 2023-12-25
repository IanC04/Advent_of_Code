/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.List;

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

        private List<Brick> supporting = new ArrayList<>();
        private List<Brick> supports = new ArrayList<>();

        private Brick(String name, int x1, int x2, int y1, int y2, int z1, int z2) {
            this.name = name;
            this.x1 = Math.min(x1, x2);
            this.x2 = Math.max(x1, x2);
            this.y1 = Math.min(y1, y2);
            this.y2 = Math.max(y1, y2);
            this.z1 = Math.min(z1, z2);
            this.z2 = Math.max(z1, z2);
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
                bricks.add(new Brick((char) (index++ + 'A') + "", first[0], second[0], first[1],
                        second[1],
                        first[2],
                        second[2]));
            }
            bricks.sort(Comparator.comparingInt(a -> a.z1));
            fall(bricks);
            // displayTower(bricks);

            int canDisintegrate = 0;
            for (Brick brick : bricks) {
                boolean canDisintegrateSupport = true;
                for (Brick above : brick.supporting) {
                    List<Brick> all_supports = above.supports;
                    if (all_supports.size() == 1) {
                        canDisintegrateSupport = false;
                        break;
                    }
                }
                if (canDisintegrateSupport) {
                    ++canDisintegrate;
                    // System.out.println(brick + " can disintegrate");
                }
            }

            System.out.println(canDisintegrate);
        }
    }

    private static void fall(ArrayList<Brick> bricks) {
        for (int i = 0; i < bricks.size(); ++i) {
            Brick current = bricks.get(i);
            for (int j = 0; j < i; ++j) {
                Brick below = bricks.get(j);
                if (below.x1 <= current.x2 && below.x2 >= current.x1 && below.y1 <= current.y2 && below.y2 >= current.y1 && below.z2 < current.z1) {
                    current.supports.add(below);
                    below.supporting.add(current);
                }
            }
            // Drop to zero or right above another brick
            int shift =
                    current.supports.isEmpty() ? current.z1 :
                            current.z1 - current.supports.stream().map(x -> x.z2).max(Integer::compare).get() - 1;
            current.z1 -= shift;
            current.z2 -= shift;
            // System.out.println(current + " fell " + shift + " units");
        }

        for (Brick brick : bricks) {
            brick.supporting =
                    brick.supporting.stream().filter(b -> b.z1 == brick.z2 + 1).toList();
            brick.supports = brick.supports.stream().filter(b -> b.z2 == brick.z1 - 1).toList();
        }
    }

    private static void displayTower(List<Brick> bricks) {
        int max = bricks.stream().mapToInt(b -> b.z2).max().orElse(0);
        int min = bricks.stream().mapToInt(b -> b.z1).min().orElse(0);
        String[] tower = new String[max - min + 1];
        for (Brick brick : bricks) {
            for (int i = brick.z1; i <= brick.z2; i++) {
                tower[i - min] = tower[i - min] == null ? brick.name : tower[i - min] + brick.name;
            }
        }
        for (int i = tower.length - 1; i >= 0; i--) {
            System.out.println(tower[i]);
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