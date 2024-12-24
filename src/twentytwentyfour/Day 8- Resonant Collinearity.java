/**
 * @author: Ian Chen
 * @date: 12/28/2024
 */

package twentytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class ResonantCollinearity {
    private static final int DAY = 8;

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private record Coord(int i, int j) {
        private boolean inBounds(int gridLength, int gridWidth) {
            return i >= 0 && i < gridLength && j >= 0 && j < gridWidth;
        }

        public String toString() {
            return "(" + i + ", " + j + ")";
        }

        private Coord add(Coord c) {
            return new Coord(i + c.i, j + c.j);
        }

        private Coord subtract(Coord c) {
            return new Coord(i - c.i, j - c.j);
        }
    }

    private static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("2024 Input/day" + DAY + "_input.txt"));
        char[][] grid = grid(lines);
        Map<Character, Set<Coord>> antennas = new HashMap<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char c = grid[i][j];
                if (c == '.') {
                    continue;
                }
                if (!antennas.containsKey(c)) {
                    antennas.put(c, new HashSet<>());
                }
                antennas.get(c).add(new Coord(i, j));
            }
        }
        int length = grid.length, width = grid[0].length;
        Set<Coord> antinodes = new HashSet<>();
        for (Set<Coord> set : antennas.values()) {
            List<Coord> list = set.stream().toList();
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    Coord c1 = list.get(i), c2 = list.get(j);
                    Coord diff = c1.subtract(c2);
                    Coord n1 = c1.add(diff), n2 = c2.subtract(diff);
                    if (n1.inBounds(length, width)) {
                        antinodes.add(n1);
                    }
                    if (n2.inBounds(length, width)) {
                        antinodes.add(n2);
                    }
                }
            }
        }
        System.out.println(antinodes.size());
    }

    private static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("2024 Input/day" + DAY + "_input.txt"));
        char[][] grid = grid(lines);
        Map<Character, Set<Coord>> antennas = new HashMap<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char c = grid[i][j];
                if (c == '.') {
                    continue;
                }
                if (!antennas.containsKey(c)) {
                    antennas.put(c, new HashSet<>());
                }
                antennas.get(c).add(new Coord(i, j));
            }
        }
        int length = grid.length, width = grid[0].length;
        Set<Coord> antinodes = new HashSet<>();
        for (Set<Coord> set : antennas.values()) {
            List<Coord> list = set.stream().toList();
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    Coord c1 = list.get(i), c2 = list.get(j);
                    Coord diff = c1.subtract(c2);
                    while (c1.inBounds(length, width)) {
                        antinodes.add(c1);
                        c1 = c1.add(diff);
                    }
                    while (c2.inBounds(length, width)) {
                        antinodes.add(c2);
                        c2 = c2.subtract(diff);
                    }
                }
            }
        }
        System.out.println(antinodes.size());
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
}