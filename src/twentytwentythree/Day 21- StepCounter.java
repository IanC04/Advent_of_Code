/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.*;
import java.util.Queue;
import java.util.Scanner;

class StepCounter {
    private static final int DAY = 21;

    public static void main(String[] args) throws IOException {
        // part1();
        part2();
    }

    private static void part1() throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("2023 Input/day" + DAY +
                "_input.txt"))) {
            char[][] grid = bf.lines().toList().stream().map(String::toCharArray).toArray(char[][]::new);
            boolean[][] visited = new boolean[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; ++i) {
                for (int j = 0; j < grid[0].length; ++j) {
                    if (grid[i][j] == 'S') {
                        System.out.println(bfs(grid, visited, i, j));
                        break;
                    }
                }
            }

            printGrid(grid, visited);
        }
    }

    private enum Direction {
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

        final int dr, dc;

        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }
    }

    private record Block(int row, int col, int steps) {
    }
    private static int bfs(char[][] grid, boolean[][] visited, int r, int c) {
        int sum = 0;
        int steps = 64;
        Queue<Block> queue = new java.util.LinkedList<>();
        queue.add(new Block(r, c, 0));

        while (!queue.isEmpty()) {
            Block block = queue.poll();
            assert block != null;
            if(visited[block.row][block.col]) {
                continue;
            }
            if (block.steps % 2 == 0) {
                visited[block.row][block.col] = true;
                ++sum;
            }
            // visited[block.row][block.col] = true;

            for (Direction direction : Direction.values()) {
                int newRow = block.row + direction.dr;
                int newCol = block.col + direction.dc;
                if (inBounds(newRow, newCol, grid, visited) && block.steps + 1 <= steps) {
                    queue.add(new Block(newRow, newCol, block.steps + 1));
                }
            }
        }

        return sum;
    }

    private static boolean inBounds(int r, int c, char[][] grid, boolean[][] visited) {
            return !(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || visited[r][c] ||
                    grid[r][c] == '#');
    }
    private static void printGrid(char[][] grid, boolean[][] visited) {
        int oTotal = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length ; ++j) {
                if (visited[i][j]) {
                    oTotal++;
                    System.out.print("O");
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println(oTotal);
        }
    }

    private static void part2() throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("2023 Input/day" + DAY +
                "_input.txt"))) {
            char[][] grid = bf.lines().toList().stream().map(String::toCharArray).toArray(char[][]::new);
            boolean[][] visited = new boolean[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; ++i) {
                for (int j = 0; j < grid[0].length; ++j) {
                    if (grid[i][j] == 'S') {
                        System.out.println(bfs(grid, visited, i, j));
                        break;
                    }
                }
            }

            printGrid(grid, visited);
        }
    }
}