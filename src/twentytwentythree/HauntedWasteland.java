/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */
package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

class HauntedWasteland {
    private static final int DAY = 8;

    public static void main(String[] args) throws FileNotFoundException {
        // No BFS since using a predetermined path
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            String path = s.nextLine();
            s.nextLine();

            HashMap<String, Node> map = new HashMap<>();
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] line_nodes = line.split("[^\\w]+");
                Node parent = map.getOrDefault(line_nodes[0], new Node(line_nodes[0]));
                map.put(line_nodes[0], parent);
                Node left = map.getOrDefault(line_nodes[1], new Node(line_nodes[1]));
                map.put(line_nodes[1], left);
                Node right = map.getOrDefault(line_nodes[2], new Node(line_nodes[2]));
                map.put(line_nodes[2], right);
                parent.left = left;
                parent.right = right;
            }

            Node current = map.get("AAA");
            boolean atZZZ = false;
            int index = 0, steps = 0;
            while (!atZZZ) {
                // System.out.println(current.name + " at step: " + steps);
                current = path.charAt(index) == 'L' ? current.left : current.right;
                atZZZ = current.name.equals("ZZZ");
                ++steps;
                index = (index + 1) % path.length();
            }

            System.out.println(steps);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            String path = s.nextLine();
            s.nextLine();

            HashMap<String, Node> map = new HashMap<>();
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] line_nodes = line.split("[^\\w]+");
                Node parent = map.getOrDefault(line_nodes[0], new Node(line_nodes[0]));
                map.put(line_nodes[0], parent);
                Node left = map.getOrDefault(line_nodes[1], new Node(line_nodes[1]));
                map.put(line_nodes[1], left);
                Node right = map.getOrDefault(line_nodes[2], new Node(line_nodes[2]));
                map.put(line_nodes[2], right);
                parent.left = left;
                parent.right = right;
            }

            List<Node> nodes = new ArrayList<>();
            for (Node n : map.values()) {
                if (n.name.endsWith("A")) {
                    nodes.add(n);
                }
            }

            boolean alZ = false;
            int index = 0;
            long steps = 0;

            HashMap<Node, Integer> stepsNeeded = new HashMap<>();
            while (!alZ) {
                // System.out.println(nodes);
                int finalIndex = index;
                nodes = nodes.stream().map(n -> path.charAt(finalIndex) == 'L' ? n.left : n.right).toList();
                alZ = nodes.stream().allMatch(n -> n.name.endsWith("Z"));
                ++steps;
                int finalSteps = (int) steps;
                nodes.stream().filter(n -> n.name.endsWith("Z")).forEach(n -> stepsNeeded.put(n, finalSteps));
                nodes = nodes.stream().filter(n -> !n.name.endsWith("Z")).toList();
                index = (index + 1) % path.length();
            }

            // System.out.println(stepsNeeded);

            // Get cycle length
            steps = LCM(stepsNeeded.values().stream().mapToInt(i -> i).toArray());
            System.out.println(steps);
        }
    }

    private static long LCM(int[] arr) {
        long lcm = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            // Learned from Number Theory: lcm(a, b) = a * b / gcd(a, b)
            lcm = lcm * arr[i] / GCD(lcm, arr[i]);
        }
        return lcm;
    }

    /**
     * Euclidean Algorithm
     */
    private static long GCD(long a, long b) {
        if (b == 0) {
            return a;
        }
        return GCD(b, a%b);
    }

    private static class Node {
        private String name;

        private Node left;
        private Node right;

        public Node(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}