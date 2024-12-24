/**
 * @author: Ian Chen
 * @date: 12/26/2024
 */

package twentytwentyfour;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class GuardGallivant {
    private static final int DAY = 6;
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            List<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                lines.add(s.nextLine());
            }
            char[][] grid = grid(lines);
            int x = 0, y = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == '^') {
                        x = i;
                        y = j;
                        grid[i][j] = '.';
                        break;
                    }
                }
            }
            int steps = 0, dir = 0;
            while (true) {
                if (grid[x][y] != 'X') {
                    steps++;
                }
                grid[x][y] = 'X';
                int nextX = x + DIRECTIONS[dir][0], nextY = y + DIRECTIONS[dir][1];
                if (!(nextX >= 0 && nextX < grid.length && nextY >= 0 && nextY < grid[0].length)) {
                    break;
                }
                while (grid[nextX][nextY] == '#') {
                    dir = (dir + 1) % 4;
                    nextX = x + DIRECTIONS[dir][0];
                    nextY = y + DIRECTIONS[dir][1];
                }
                x = nextX;
                y = nextY;
            }
            System.out.println(steps);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            List<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                lines.add(s.nextLine());
            }
            char[][] grid = grid(lines);
            int realX = 0, realY = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == '^') {
                        realX = i;
                        realY = j;
                        grid[i][j] = '.';
                        break;
                    }
                }
            }
            int cycles = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    boolean[][][] dirCache = new boolean[grid.length][grid[0].length][4];
                    int dir = 0, x = realX, y = realY;
                    if (grid[i][j] == '#' || (x == i && y == j)) {
                        continue;
                    }
                    grid[i][j] = '#';
                    while (true) {
                        int nextX = x + DIRECTIONS[dir][0], nextY = y + DIRECTIONS[dir][1];
                        if (!(nextX >= 0 && nextX < grid.length && nextY >= 0 && nextY < grid[0].length)) {
                            break;
                        }
                        while (grid[nextX][nextY] == '#') {
                            dir = (dir + 1) % 4;
                            nextX = x + DIRECTIONS[dir][0];
                            nextY = y + DIRECTIONS[dir][1];
                        }
                        if (dirCache[x][y][dir]) {
                            cycles++;
                            break;
                        }
                        dirCache[x][y][dir] = true;
                        x = nextX;
                        y = nextY;
                    }
                    grid[i][j] = '.';
                }
            }
            System.out.println(cycles);
        }
    }

    private static char[][] grid(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                grid[i][j] = lines.get(i).charAt(j);
            }
        }
        return grid;
    }
}