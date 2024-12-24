/**
 * @author: Ian Chen
 * @date: 12/24/2024
 */

package twentytwentyfour;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class CeresSearch {
    private static final int DAY = 4;
    private static final char[] LETTERS = {'X', 'M', 'A', 'S'};
    private static final int[][] DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}};

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

            int result = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    for (int[] dir : DIRECTIONS) {
                        result += dfs(grid, i, j, dir, 0);
                    }
                }
            }
            System.out.println(result);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            List<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                lines.add(s.nextLine());
            }
            char[][] grid = grid(lines);

            int result = 0;
            for (int i = 1; i < grid.length - 1; i++) {
                for (int j = 1; j < grid[i].length - 1; j++) {
                    if (grid[i][j] == 'A') {
                        boolean slantOne =
                                (grid[i - 1][j - 1] == 'M' && grid[i + 1][j + 1] == 'S') || (grid[i - 1][j - 1] == 'S' && grid[i + 1][j + 1] == 'M');
                        boolean slantTwo =
                                (grid[i - 1][j + 1] == 'M' && grid[i + 1][j - 1] == 'S') || (grid[i - 1][j + 1] == 'S' && grid[i + 1][j - 1] == 'M');
                        if (slantOne && slantTwo) {
                            result++;
                        }
                    }
                }
            }
            System.out.println(result);
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

    private static int dfs(char[][] grid, int row, int col, int[] dir, int index) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return 0;
        }
        if (index == 3 && grid[row][col] == 'S') {
            return 1;
        }
        int sum = 0;
        if (grid[row][col] == LETTERS[index]) {
            sum += dfs(grid, row + dir[0], col + dir[1], dir, index + 1);
        }

        return sum;
    }
}