/**
 * @author: Ian Chen
 * @date: 12/30/2024
 */

package twentytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class GardenGroups {

    public static void main(String[] args) throws IOException {
        String lines = String.join("\n", Files.readAllLines(Path.of("2024 Input/day12_input.txt")));
        part1(lines);
        part2(lines);
    }

    private static class Region {
        int area, perimeter, vertices;
        char key;

        private Region(char key) {
            this.key = key;
        }
    }

    private static void part1(String lines) {
        char[][] grid = Arrays.stream(lines.split("\n")).map(String::toCharArray).toArray(char[][]::new);
        int price = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Region region = new Region(grid[i][j]);
                dfs(i, j, grid, visited, region);
                price += region.area * region.perimeter;
            }
        }
        System.out.println(price);
    }

    private static void part2(String lines) {
        char[][] grid = Arrays.stream(lines.split("\n")).map(String::toCharArray).toArray(char[][]::new);
        int price = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Region region = new Region(grid[i][j]);
                dfs(i, j, grid, visited, region);
                price += region.area * region.vertices;
            }
        }
        System.out.println(price);
    }

    private static boolean dfs(int r, int c, char[][] grid, boolean[][] visited, Region region) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[r].length) {
            return true;
        }
        if (grid[r][c] == region.key) {
            if (visited[r][c]) {
                return false;
            }
            if (hasUpperLeftCorner(grid, r, c)) {
                region.vertices++;
            }
            if (hasUpperRightCorner(grid, r, c)) {
                region.vertices++;
            }
            if (hasLowerLeftCorner(grid, r, c)) {
                region.vertices++;
            }
            if (hasLowerRightCorner(grid, r, c)) {
                region.vertices++;
            }
            visited[r][c] = true;
            region.area += 1;
            boolean attaches;
            attaches = dfs(r - 1, c, grid, visited, region);
            region.perimeter += attaches ? 1 : 0;
            attaches = dfs(r, c + 1, grid, visited, region);
            region.perimeter += attaches ? 1 : 0;
            attaches = dfs(r + 1, c, grid, visited, region);
            region.perimeter += attaches ? 1 : 0;
            attaches = dfs(r, c - 1, grid, visited, region);
            region.perimeter += attaches ? 1 : 0;
        }
        return grid[r][c] != region.key;
    }

    private static boolean isNeighbor(char[][] grid, int r, int c, char key) {
        return r >= 0 && c >= 0 && r < grid.length && c < grid[r].length && grid[r][c] == key;
    }

    private static boolean hasUpperLeftCorner(char[][] grid, int r, int c) {
        return !isNeighbor(grid, r, c - 1, grid[r][c]) && !isNeighbor(grid, r - 1, c, grid[r][c])
                || isNeighbor(grid, r, c - 1, grid[r][c]) && !isNeighbor(grid, r - 1, c - 1, grid[r][c]) && isNeighbor(grid, r - 1, c, grid[r][c]);
    }

    private static boolean hasUpperRightCorner(char[][] grid, int r, int c) {
        return !isNeighbor(grid, r, c + 1, grid[r][c]) && !isNeighbor(grid, r - 1, c, grid[r][c])
                || isNeighbor(grid, r, c + 1, grid[r][c]) && !isNeighbor(grid, r - 1, c + 1, grid[r][c]) && isNeighbor(grid, r - 1, c, grid[r][c]);
    }

    private static boolean hasLowerLeftCorner(char[][] grid, int r, int c) {
        return !isNeighbor(grid, r, c - 1, grid[r][c]) && !isNeighbor(grid, r + 1, c, grid[r][c])
                || isNeighbor(grid, r, c - 1, grid[r][c]) && !isNeighbor(grid, r + 1, c - 1, grid[r][c]) && isNeighbor(grid, r + 1, c, grid[r][c]);
    }

    private static boolean hasLowerRightCorner(char[][] grid, int r, int c) {
        return !isNeighbor(grid, r, c + 1, grid[r][c]) && !isNeighbor(grid, r + 1, c, grid[r][c])
                || isNeighbor(grid, r, c + 1, grid[r][c]) && !isNeighbor(grid, r + 1, c + 1, grid[r][c]) && isNeighbor(grid, r + 1, c, grid[r][c]);
    }
}