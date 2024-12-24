/**
 * @author: Ian Chen
 * @date: 1/1/2025
 */

package twentytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class RestroomRedoubt {
    private static final int WIDTH = 101, HEIGHT = 103, SECONDS = 100;

    public static void main(String[] args) throws IOException {
        String lines = String.join("\n", Files.readAllLines(Path.of("2024 Input/day14_input.txt")));
        part1(lines);
        part2(lines);
    }

    private static void part1(String lines) {
        Scanner scanner = new Scanner(lines);
        scanner.useDelimiter("[^0-9-]+");
        int[] quadrants = new int[4];
        while (scanner.hasNextInt()) {
            int x = scanner.nextInt(), y = scanner.nextInt();
            int dx = scanner.nextInt(), dy = scanner.nextInt();
            int endX = (x + SECONDS * dx) % WIDTH, endY = (y + SECONDS * dy) % HEIGHT;
            endX = endX < 0 ? endX + WIDTH : endX;
            endY = endY < 0 ? endY + HEIGHT : endY;
            if (endX < WIDTH / 2 && endY < HEIGHT / 2) {
                quadrants[0]++;
            }
            if (endX > WIDTH / 2 && endY < HEIGHT / 2) {
                quadrants[1]++;
            }
            if (endX < WIDTH / 2 && endY > HEIGHT / 2) {
                quadrants[2]++;
            }
            if (endX > WIDTH / 2 && endY > HEIGHT / 2) {
                quadrants[3]++;
            }
        }
        System.out.println(quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3]);
    }

    private static void part2(String lines) {
        Scanner scanner = new Scanner(lines);
        scanner.useDelimiter("[^0-9-]+");
        Set<Robot> robots = new HashSet<>();
        while (scanner.hasNextInt()) {
            robots.add(new Robot(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
        char[][] grid = new char[HEIGHT][WIDTH];
        for (int i = 0; i < 100_000; i++) {
            Arrays.stream(grid).forEach(row -> Arrays.fill(row, '.'));
            boolean overlap = false;
            for (Robot robot : robots) {
                if (grid[robot.y][robot.x] == '*') {
                    overlap = true;
                } else {
                    grid[robot.y][robot.x] = '*';
                }
                robot.update();
            }
            if (!overlap) {
                for (char[] row : grid) {
                    for (char c : row) {
                        System.out.print(c);
                    }
                    System.out.println();
                }
                System.out.println(i);
                break;
            }
        }
    }

    private static class Robot {
        int x, y, dx, dy;

        private Robot(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        private void update() {
            x += dx;
            x %= WIDTH;
            x = x < 0 ? x + WIDTH : x;
            y += dy;
            y %= HEIGHT;
            y = y < 0 ? y + HEIGHT : y;
        }
    }
}