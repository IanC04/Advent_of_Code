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

class ParabolicReflectorDish {
    private static final int DAY = 14;

    private static final int BILLION = 1_000_000_000;

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

            rollNorth(grid);

            long weight = calculateWeight(grid);

            System.out.println(weight);
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

            ArrayList<char[][]> grids = new ArrayList<>();
            grids.add(grid);

            int cycleStart = 0, cycleEnd = 0;
            for (int i = 1; i < BILLION; ++i) {
                grid = Arrays.stream(grid).map(char[]::clone).toArray(char[][]::new);
                rollNorth(grid);
                rollWest(grid);
                rollSouth(grid);
                rollEast(grid);
                grids.add(grid);
                // printGrid(grid);

                int start = findCycle(grids);
                if (start != -1) {
                    cycleStart = start;
                    cycleEnd = i;
                    break;
                }
            }

            assert cycleStart != 0 && cycleEnd != 0 : "Cycle not found";
            // No add 1 since cycleEnd is inclusive
            int cycleLength = cycleEnd - cycleStart;
            grid = grids.get(((BILLION - cycleStart) % cycleLength) + cycleStart);
            // grids.forEach(x -> System.out.println(calculateWeight(x)));

            long weight = calculateWeight(grid);
            System.out.println(weight);
        }
    }


    private static long calculateWeight(char[][] grid) {
        long weight = 0;
        for (int i = 0; i < grid.length; i++) {
            for (char c : grid[i]) {
                if (c == 'O') {
                    weight += grid.length - i;
                }
            }
        }
        return weight;
    }

    private static void rollNorth(char[][] grid) {
        assert grid.length > 0;

        int[] topWall = new int[grid[0].length];
        Arrays.fill(topWall, -1);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j]) {
                    case '#':
                        topWall[j] = i;
                        break;
                    case 'O':
                        grid[i][j] = '.';
                        grid[++topWall[j]][j] = 'O';
                        break;
                }
            }
        }
    }

    private static void rollWest(char[][] grid) {
        assert grid.length > 0;

        int leftWall = -1;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                switch (grid[i][j]) {
                    case '#':
                        leftWall = j;
                        break;
                    case 'O':
                        grid[i][j] = '.';
                        grid[i][++leftWall] = 'O';
                        break;
                }
            }
            leftWall = -1;
        }
    }

    private static void rollSouth(char[][] grid) {
        assert grid.length > 0;

        int[] bottomWall = new int[grid[0].length];
        Arrays.fill(bottomWall, grid.length);
        for (int i = grid.length - 1; i >= 0; --i) {
            for (int j = 0; j < grid[i].length; ++j) {
                switch (grid[i][j]) {
                    case '#':
                        bottomWall[j] = i;
                        break;
                    case 'O':
                        grid[i][j] = '.';
                        grid[--bottomWall[j]][j] = 'O';
                        break;
                }
            }
        }
    }

    private static void rollEast(char[][] grid) {
        assert grid.length > 0;

        int rightWall = grid[0].length;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = grid[i].length - 1; j >= 0; --j) {
                switch (grid[i][j]) {
                    case '#':
                        rightWall = j;
                        break;
                    case 'O':
                        grid[i][j] = '.';
                        grid[i][--rightWall] = 'O';
                        break;
                }
            }
            rightWall = grid[0].length;
        }
    }

    private static int findCycle(ArrayList<char[][]> grids) {
        int j = grids.size() - 1;
        for (int i = 0; i < j; ++i) {
            if (Arrays.deepEquals(grids.get(i), grids.get(j))) {
                return i;
            }
        }

        return -1;
    }

    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }
}