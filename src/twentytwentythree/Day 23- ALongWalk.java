/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

class ALongWalk {
    private static final int DAY = 23;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        // part2();
    }

    private static class Node {
        private final String name;

        private final HashMap<Node, Integer> edges;

        private Node(String name) {
            this.name = name;
            edges = new HashMap<>();
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }

            char[][] grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);
            Node root = graphify(grid);
            List<Integer> paths = new ArrayList<>();
            printNodes(root, paths,  0);
            System.out.println(paths.stream().mapToInt(Integer::intValue).max().orElse(-1));

            // Topological sort

        }
    }

    private static void printNodes(Node current, List<Integer> paths, int distance) {
        if (current.edges.isEmpty()) {
            assert current.name.equals("end");
            paths.add(distance);
            // System.out.println("[" + distance + "] " + current);
            return;
        }
        for (Node node : current.edges.keySet()) {
            printNodes(node, paths, distance + current.edges.get(node));
        }
    }

    private enum Direction {
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);
        final int dr, dc;

        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }

        private Direction backwards() {
            return switch (this) {
                case UP -> DOWN;
                case DOWN -> UP;
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }
    }

    private record Position(int r, int c, Direction direction, Node previous, int steps) {
    }

    /**
     * Graph should be acyclic
     *
     * @param grid
     */
    private static Node graphify(char[][] grid) {
        int startC = String.valueOf(grid[0]).indexOf('.');
        Node root = new Node("root");
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(0, startC, Direction.DOWN, root, 0));
        while (!queue.isEmpty()) {
            Position position = queue.poll();
            int r = position.r();
            int c = position.c();
            for (Direction direction : Direction.values()) {
                if (direction.backwards() == position.direction) {
                    continue;
                }
                int dr = direction.dr;
                int dc = direction.dc;
                int newR = r + dr, newC = c + dc;
                if (newR >= grid.length || newR < 0 || newC >= grid[newR].length || newC < 0) {
                    continue;
                }
                char val = grid[newR][newC];
                if (newR == grid.length - 1 && val == '.') {
                    Node end = new Node("end");
                    position.previous.edges.put(end, position.steps + 1);
                    continue;
                }
                Node node = new Node("[" + newR + "," + newC + "]");
                switch (val) {
                    case '#':
                        continue;
                    case '.':
                        queue.add(new Position(newR, newC, direction, position.previous,
                                position.steps + 1));
                        break;
                    case '^':
                        if (direction == Direction.DOWN) {
                            continue;
                        }
                        queue.add(new Position(newR, newC, direction, node, 0));
                        position.previous.edges.put(node, position.steps + 1);
                        break;
                    case '>':
                        if (direction == Direction.LEFT) {
                            continue;
                        }
                        queue.add(new Position(newR, newC, direction, node, 0));
                        position.previous.edges.put(node, position.steps + 1);
                        break;
                    case 'v':
                        if (direction == Direction.UP) {
                            continue;
                        }
                        queue.add(new Position(newR, newC, direction, node, 0));
                        position.previous.edges.put(node, position.steps + 1);
                        break;
                    case '<':
                        if (direction == Direction.RIGHT) {
                            continue;
                        }
                        queue.add(new Position(newR, newC, direction, node, 0));
                        position.previous.edges.put(node, position.steps + 1);
                        break;
                }
            }
        }

        return root;
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