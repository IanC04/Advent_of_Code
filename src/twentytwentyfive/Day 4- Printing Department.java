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
class PrintingDepartment {

    private final static int[][] directions =
            {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 4.txt"));
        part1(input);
        input = new Scanner(new File("2025 Input/day 4.txt"));
        part2(input);
    }

    private static void part1(Scanner file) {
        List<String> lines = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            lines.add(line);
        }

        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        int total = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '@' && countRolls(grid, i, j) < 4) {
                    total++;
                }
            }
        }

        System.out.println(total);
    }

    private static boolean inBounds(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static int countRolls(char[][] grid, int row, int col) {
        int count = 0;
        for (int[] dir : directions) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (inBounds(grid, x, y)) {
                if (grid[x][y] == '@') {
                    count++;
                }
            }
        }

        return count;
    }

    private static void part2(Scanner file) {
        List<String> lines = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            lines.add(line);
        }

        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        int total = 0;

        int removed;
        do {
            removed = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == '@' && countRolls(grid, i, j) < 4) {
                        removed++;
                        grid[i][j] = '.';
                    }
                }
            }
            total += removed;
        }
        while (removed != 0);

        System.out.println(total);
    }
}