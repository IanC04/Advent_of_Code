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

class TheFloorWillBeLava {
    private static final int DAY = 16;

    private enum Directions {
        UP(-1, 0, 0), DOWN(1, 0, 1), LEFT(0, -1, 2), RIGHT(0, 1, 3);

        private final int dx;
        private final int dy;

        private final int index;

        Directions(int dx, int dy, int index) {
            this.dx = dx;
            this.dy = dy;
            this.index = index;
        }

        static Directions getElement(int index) {
            return Directions.values()[index];
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }

            assert !lines.isEmpty();
            char[][] grid = new char[lines.size()][lines.getFirst().length()];
            for (int i = 0; i < lines.size(); i++) {
                grid[i] = lines.get(i).toCharArray();
            }

            boolean[][] energized = new boolean[grid.length][grid[0].length];
            boolean[][][] visited = new boolean[grid.length][grid[0].length][4];
            lightPath(grid, 0, 0, Directions.RIGHT, energized, visited);


            System.out.println(getCount(energized));
        }
    }

    private static long getCount(boolean[][] energized) {
        long count = 0;
        for (boolean[] row : energized) {
            for (boolean cell : row) {
                if (cell) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void lightPath(char[][] grid, int r, int c, Directions dir,
                                  boolean[][] energized, boolean[][][] visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || (visited[r][c][dir.index])) {
            return;
        }
        energized[r][c] = true;
        visited[r][c][dir.index] = true;

        switch (grid[r][c]) {
            case '.': // Empty space
                lightPath(grid, r + dir.dx, c + dir.dy, dir, energized, visited);
                break;
            case '/':
                if (dir == Directions.UP) {
                    lightPath(grid, r, c + 1, Directions.RIGHT, energized, visited);
                } else if (dir == Directions.DOWN) {
                    lightPath(grid, r, c - 1, Directions.LEFT, energized, visited);
                } else if (dir == Directions.LEFT) {
                    lightPath(grid, r + 1, c, Directions.DOWN, energized, visited);
                } else {
                    lightPath(grid, r - 1, c, Directions.UP, energized, visited);
                }
                break;
            case '\\':
                if (dir == Directions.UP) {
                    lightPath(grid, r, c - 1, Directions.LEFT, energized, visited);
                } else if (dir == Directions.DOWN) {
                    lightPath(grid, r, c + 1, Directions.RIGHT, energized, visited);
                } else if (dir == Directions.LEFT) {
                    lightPath(grid, r - 1, c, Directions.UP, energized, visited);
                } else {
                    lightPath(grid, r + 1, c, Directions.DOWN, energized, visited);
                }
                break;
            case '|':
                if (dir == Directions.UP || dir == Directions.DOWN) {
                    lightPath(grid, r + dir.dx, c + dir.dy, dir, energized, visited);
                } else {
                    lightPath(grid, r - 1, c, Directions.UP, energized, visited);
                    lightPath(grid, r + 1, c, Directions.DOWN, energized, visited);
                }
                break;
            case '-':
                if (dir == Directions.LEFT || dir == Directions.RIGHT) {
                    lightPath(grid, r + dir.dx, c + dir.dy, dir, energized, visited);
                } else {
                    lightPath(grid, r, c - 1, Directions.LEFT, energized, visited);
                    lightPath(grid, r, c + 1, Directions.RIGHT, energized, visited);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid character: " + grid[r][c]);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }

            assert !lines.isEmpty();
            char[][] grid = new char[lines.size()][lines.getFirst().length()];
            for (int i = 0; i < lines.size(); i++) {
                grid[i] = lines.get(i).toCharArray();
            }

            long[][][] maxEnergy = new long[grid.length][grid[0].length][4];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    for (int k = 0; k < 4; k++) {
                        // Solution if beam could start anywhere with any orientation
                        // if (maxEnergy[i][j][k] != 0) {
                        //     continue;
                        // }
                        switch (k) {
                            case 0:
                                if (i != grid.length - 1) {
                                    continue;
                                }
                                break;
                            case 1:
                                if (i != 0) {
                                    continue;
                                }
                                break;
                            case 2:
                                if (j != grid[i].length - 1) {
                                    continue;
                                }
                                break;
                            case 3:
                                if (j != 0) {
                                    continue;
                                }
                                break;
                        }

                        boolean[][] energized = new boolean[grid.length][grid[0].length];
                        boolean[][][] visited = new boolean[grid.length][grid[0].length][4];
                        lightPath(grid, i, j, Directions.getElement(k), energized, visited);
                        long currentEnergy = getCount(energized);

                        for (int l = 0; l < visited.length; l++) {
                            for (int m = 0; m < visited[l].length; m++) {
                                for (int n = 0; n < visited[l][m].length; n++) {
                                    if (visited[l][m][n]) {
                                        maxEnergy[l][m][n] = Math.max(maxEnergy[l][m][n], currentEnergy);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            long max = Arrays.stream(maxEnergy).flatMap(Arrays::stream).flatMapToLong(Arrays::stream).max().orElseThrow();

            System.out.println(max);
        }
    }
}