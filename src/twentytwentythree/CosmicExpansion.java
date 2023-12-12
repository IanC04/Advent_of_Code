/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

class CosmicExpansion {
    private static final int DAY = 11;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<ArrayList<Character>> galaxy = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                ArrayList<Character> row =
                        line.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(ArrayList::new));
                galaxy.add(row);
            }

            ArrayList<Integer> emptyRows = new ArrayList<>();
            for (int i = galaxy.size() - 1; i >= 0; --i) {
                if (emptyRow(galaxy.get(i))) {
                    emptyRows.add(i);
                }
            }

            ArrayList<Integer> emptyColumns = new ArrayList<>();
            for (int i = galaxy.getFirst().size() - 1; i >= 0; --i) {
                if (emptyColumn(galaxy, i)) {
                    emptyColumns.add(i);
                }
            }

            long sum = 0;
            for (int i = 0; i < galaxy.size(); i++) {
                for (int j = 0; j < galaxy.get(i).size(); j++) {
                    if (galaxy.get(i).get(j) == '#') {
                        sum += getAllSuccessiveDistances(galaxy, emptyRows, emptyColumns, i, j);
                    }
                }
            }

            System.out.println(sum);
        }
    }

    private static boolean emptyRow(ArrayList<Character> row) {
        for (char c : row) {
            if (c == '#') {
                return false;
            }
        }
        return true;
    }

    private static boolean emptyColumn(ArrayList<ArrayList<Character>> galaxy, int column) {
        for (ArrayList<Character> row : galaxy) {
            if (row.get(column) == '#') {
                return false;
            }
        }
        return true;
    }

    private static long getAllSuccessiveDistances(ArrayList<ArrayList<Character>> galaxy,
                                                  ArrayList<Integer> emptyRows,
                                                  ArrayList<Integer> emptyColumns, int r,
                                                  int c) {
        long sum = 0;
        for (int i = r; i < galaxy.size(); i++) {
            for (int j = i == r ? c : 0; j < galaxy.get(i).size(); j++) {
                if (galaxy.get(i).get(j) == '#') {
                    int finalI = i;
                    /// 999_999L for part 2
                    long rowDistance =
                            emptyRows.stream().filter(row -> row > r && row < finalI).toArray().length * 999_999L + Math.abs(i - r);
                    int finalJ = j;
                    /// 999_999L for part 2
                    long columnDistance =
                            emptyColumns.stream().filter(column -> (column > c && column < finalJ) || (column > finalJ && column < c)).toArray().length * 999_999L + Math.abs(j - c);
                    sum += rowDistance + columnDistance;
                }
            }
        }
        return sum;
    }

    private static void part2() throws FileNotFoundException {
        part1();
    }
}