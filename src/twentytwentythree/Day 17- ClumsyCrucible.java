/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

class ClumsyCrucible {
    private static final int DAY = 17;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        // part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }

            byte[][] grid = new byte[lines.size()][lines.getFirst().length()];
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(i).length(); j++) {
                    grid[i][j] = (byte) (lines.get(i).charAt(j) - '0');
                }
            }

            Node end = getSmallestSumPath(grid);
            assert end != null;
            long minHeatLoss = end.heatLoss;
            // printPath(end, grid.length, grid[0].length);
            System.out.println(minHeatLoss);
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

    private record Node(int row, int col, long heatLoss, Direction direction, int straightIndex,
                        Node previous) implements Comparable<Node> {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node node)) return false;
            return row == node.row && col == node.col && direction == node.direction && straightIndex == node.straightIndex;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, direction, straightIndex);
        }

        @Override
        public int compareTo(Node o) {
            // Prioritize nodes that have a lower heat loss
            int ret = Long.compare(heatLoss, o.heatLoss);
            if (ret == 0 && direction == o.direction) {
                // Prioritize nodes that have a shorter straight length
                ret = Integer.compare(straightIndex, o.straightIndex);
            }
            else if (ret == 0) {
                // Prioritize nodes that are closer to the end
                ret = Integer.compare(o.row + o.col, row + col);
            }
            return ret;
        }
    }

    private static Node getSmallestSumPath(byte[][] grid) {
        assert grid.length > 0 && grid[0].length > 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        HashSet<Node> visited = new HashSet<>();

        Node root = new Node(0, 0, 0, null, 0, null);
        queue.add(new Node(0, 1, grid[0][1], Direction.RIGHT, 0, root));
        queue.add(new Node(1, 0, grid[1][0], Direction.DOWN, 0, root));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            assert current != null : "current is null";
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            if (current.row == grid.length - 1 && current.col == grid[0].length - 1) {
                return current;
            }
            for (Direction direction : getTurnLeftAndRight(current.direction)) {
                int newRow = current.row + direction.dr;
                int newCol = current.col + direction.dc;
                boolean inBounds = inBounds(grid, newRow, newCol);
                if (!inBounds) {
                    continue;
                }
                Node neighbor = new Node(newRow, newCol, current.heatLoss + grid[newRow][newCol],
                        direction, 0, current);
                queue.add(neighbor);
            }
            // Max straight length is 3, [0, 1, 2]
            if (current.straightIndex < 2) {
                int newRow = current.row + current.direction.dr;
                int newCol = current.col + current.direction.dc;
                boolean inBounds = inBounds(grid, newRow, newCol);
                if (inBounds) {
                    Node neighbor = new Node(newRow, newCol, current.heatLoss + grid[newRow][newCol],
                            current.direction, current.straightIndex + 1, current);
                    queue.add(neighbor);
                }
            }
        }

        assert false : "No path found";
        return null;
    }

    private static void printPath(Node end, int rows, int cols) {
        char[][] output = new char[rows][cols];
        for (char[] chars : output) {
            Arrays.fill(chars, '.');
        }
        while (end != null) {
            output[end.row][end.col] = switch (end.direction) {
                case null -> '#';
                case UP -> '^';
                case DOWN -> 'v';
                case LEFT -> '<';
                case RIGHT -> '>';
            };
            end = end.previous;
        }
        for (char[] chars : output) {
            for (char c : chars) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static Direction[] getTurnLeftAndRight(Direction current) {
        return switch (current) {
            case UP, DOWN -> new Direction[]{Direction.LEFT, Direction.RIGHT};
            case LEFT, RIGHT -> new Direction[]{Direction.UP, Direction.DOWN};
        };
    }

    private static boolean inBounds(byte[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                System.out.println(line);
            }
        }
    }
}