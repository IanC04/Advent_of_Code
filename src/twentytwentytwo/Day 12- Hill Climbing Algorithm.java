package twentytwentytwo;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Stack;

class HillClimbingAlgorithm {
    private static final int DAY = 12;

    private static final int[][] directions = {{-1, 0, 1, 0}, {0, 1, 0, -1}};

    public static void main(String[] args) throws FileNotFoundException {
        // shortestPathLength("2022/day" + DAY + "_input.txt");
        shortestALength("2022/day" + DAY + "_input.txt");
    }

    private static void shortestPathLength(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        ArrayList<String> gridAsLines = new ArrayList<>();
        while (s.hasNextLine()) {
            gridAsLines.add(s.nextLine());
        }
        Cell[][] grid = new Cell[gridAsLines.size()][gridAsLines.get(0).length()];
        Cell start = null, end = null;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char c = gridAsLines.get(i).charAt(j);
                grid[i][j] = new Cell(c, i, j);
                if (c == 'S') {
                    start = grid[i][j];
                    start.elevation = 'a';
                } else if (c == 'E') {
                    end = grid[i][j];
                    end.elevation = 'z';
                }
            }
        }
        dijkstraLength(start, grid);
        printGridInfo(start, end, grid);
    }

    private static void shortestALength(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        ArrayList<String> gridAsLines = new ArrayList<>();
        while (s.hasNextLine()) {
            gridAsLines.add(s.nextLine());
        }
        Cell[][] grid = new Cell[gridAsLines.size()][gridAsLines.get(0).length()];
        Cell end = null;
        Stack<Cell> aCells = new Stack<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char c = gridAsLines.get(i).charAt(j);
                grid[i][j] = new Cell(c, i, j);
                if (c == 'S' || c == 'a') {
                    grid[i][j].elevation = 'a';
                    aCells.push(grid[i][j]);
                } else if (c == 'E') {
                    end = grid[i][j];
                    end.elevation = 'z';
                }
            }
        }
        int minLength = Integer.MAX_VALUE;
        while (!aCells.isEmpty()) {
            Cell start = aCells.pop();
            dijkstraLength(start, grid);
            // printGridInfo(start, end, grid);
            minLength = Math.min(minLength, end.pathLengthFromStart);
        }
        System.out.println(minLength);
    }

    private static void printGridInfo(Cell start, Cell end, Cell[][] grid) {
        System.out.println("Costs from beginning.");
        for (Cell[] line : grid) {
            for (Cell cell : line) {
                System.out.printf("%4d", cell.costFromStart);
            }
            System.out.println();
        }
        System.out.println("Path lengths from beginning.");
        for (Cell[] line : grid) {
            for (Cell cell : line) {
                System.out.printf("%4d", cell.pathLengthFromStart);
            }
            System.out.println();
        }
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                System.out.println(cell);
            }
        }
        System.out.println("Starting cell: " + start);
        System.out.println("Ending cell: " + end);
    }

    private static void dijkstraLength(Cell start, Cell[][] grid) {
        resetGrid(grid);
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        pq.add(start);
        start.costFromStart = 0;
        start.pathLengthFromStart = 0;
        while (!pq.isEmpty()) {
            Cell cell = pq.poll();
            if (!cell.visited) {
                cell.visited = true;
                for (int i = 0; i < directions[0].length; i++) {
                    int newRow = cell.row + directions[0][i], newCol = cell.col + directions[1][i];
                    if (inBounds(newRow, newCol, grid)) {
                        Cell dest = grid[newRow][newCol];
                        int climbCost = dest.elevation - cell.elevation;
                        if (climbCost <= 1) {
                            int cost = cell.costFromStart + Math.max(0, climbCost);
                            if (cell.pathLengthFromStart + 1 < dest.pathLengthFromStart) {
                                dest.costFromStart = cost;
                                dest.pathLengthFromStart = cell.pathLengthFromStart + 1;
                            }
                            pq.offer(dest);
                        }
                    }
                }
            }
        }
    }

    private static void resetGrid(Cell[][] grid) {
        for (Cell[] line : grid) {
            for (Cell cell : line) {
                cell.reset();
            }
        }
    }

    private static boolean inBounds(int row, int col, Object[][] grid) {
        // returns if positions in bounds of the array
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static class Cell implements Comparable<Cell> {
        private char elevation;

        private int costFromStart;

        private int pathLengthFromStart;

        private boolean visited;

        private int row, col;

        private Cell(char e, int row, int col) {
            elevation = e;
            this.row = row;
            this.col = col;
            reset();
        }

        public int compareTo(Cell other) {
            // return this.costFromStart - other.costFromStart;
            // comparing based on path length
            return this.pathLengthFromStart - other.pathLengthFromStart;
        }

        private void reset() {
            costFromStart = Integer.MAX_VALUE;
            pathLengthFromStart = Integer.MAX_VALUE;
            visited = false;
        }

        public String toString() {
            return "Elevation: " + elevation + ", costFromStart: " + costFromStart + ", " +
                    "pathLengthFromStart: " + pathLengthFromStart + ", visited: " + visited;
        }
    }
}