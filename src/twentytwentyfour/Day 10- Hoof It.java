/**
 * @author: Ian Chen
 * @date: 12/30/2024
 */

package twentytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class HoofIt {
    private static final int DAY = 10;

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("2024 Input/day" + DAY + "_input.txt"));
        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        int trails = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                trails += dfs(i, j, grid, '0', true);
                for (char[] row : grid) {
                    for (int k = 0; k < row.length; k++) {
                        if (row[k] == 0) {
                            row[k] = '9';
                        }
                    }
                }
            }
        }
        System.out.println(trails);
    }

    private static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("2024 Input/day" + DAY + "_input.txt"));
        char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        int trails = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                trails += dfs(i, j, grid, '0', false);
            }
        }
        System.out.println(trails);
    }

    private static int dfs(int r, int c, char[][] grid, char value, boolean part1) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) {
            return 0;
        }
        if (grid[r][c] != value) {
            return 0;
        }
        if (value == '9') {
            if (part1) {
                grid[r][c] = 0;
            }
            return 1;
        }
        return dfs(r - 1, c, grid, (char) (value + 1), part1)
                + dfs(r, c + 1, grid, (char) (value + 1), part1)
                + dfs(r + 1, c, grid, (char) (value + 1), part1)
                + dfs(r, c - 1, grid, (char) (value + 1), part1);
    }
}