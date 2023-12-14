/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

class PointofIncidence {
    private static final int DAY = 13;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            long sum = 0;
            while (s.hasNextLine()) {
                ArrayList<String> lines = new ArrayList<>();
                String line = s.nextLine();
                while (!line.isEmpty() && s.hasNextLine()) {
                    lines.add(line);
                    line = s.nextLine();
                }
                long columnPoint = 0;
                for (int i = 1; i < lines.getFirst().length(); i++) {
                    if (isColumnReflection(i, lines)) {
                        columnPoint = i;
                        break;
                    }
                }

                long rowPoint = 0;
                for (int i = 1; i < lines.size(); i++) {
                    if (isRowReflection(i, lines)) {
                        rowPoint = i;
                        break;
                    }
                }

                assert columnPoint == 0 || rowPoint == 0;
                sum += columnPoint + 100 * rowPoint;
            }

            System.out.println(sum);
        }
    }

    private static boolean isColumnReflection(int point, ArrayList<String> lines) {
        int reflectionLength = Math.min(lines.getFirst().length() - point, point);
        for (String line : lines) {
            for (int j = 1; j <= reflectionLength; j++) {
                if (line.charAt(point - j) != line.charAt(point + j - 1)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isRowReflection(int point, ArrayList<String> lines) {
        int reflectionLength = Math.min(lines.size() - point, point);
        for (int i = 0; i < lines.getFirst().length(); i++) {
            for (int j = 1; j <= reflectionLength; j++) {
                if (lines.get(point - j).charAt(i) != lines.get(point + j - 1).charAt(i)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            long sum = 0;
            while (s.hasNextLine()) {
                ArrayList<String> lines = new ArrayList<>();
                String line = s.nextLine();
                while (!line.isEmpty() && s.hasNextLine()) {
                    lines.add(line);
                    line = s.nextLine();
                }
                long columnPoint = 0;
                for (int i = 1; i < lines.getFirst().length(); i++) {
                    if (isColumnReflection2(i, lines)) {
                        columnPoint = i;
                        break;
                    }
                }

                long rowPoint = 0;
                for (int i = 1; i < lines.size(); i++) {
                    if (isRowReflection2(i, lines)) {
                        rowPoint = i;
                        break;
                    }
                }

                assert columnPoint == 0 || rowPoint == 0;
                sum += columnPoint + 100 * rowPoint;
            }

            System.out.println(sum);
        }
    }

    /**
     * Slight modification of isColumnReflection by adding a boolean to check if only one incorrect character is found
     *
     * @param point
     * @param lines
     * @return
     */
    private static boolean isColumnReflection2(int point, ArrayList<String> lines) {
        int reflectionLength = Math.min(lines.getFirst().length() - point, point);
        boolean hasIncorrect = false;
        for (String line : lines) {
            for (int j = 1; j <= reflectionLength; j++) {
                if (line.charAt(point - j) != line.charAt(point + j - 1)) {
                    if (hasIncorrect) {
                        return false;
                    } else {
                        hasIncorrect = true;
                    }
                }
            }
        }

        return hasIncorrect;
    }

    /**
     * Slight modification of isRowReflection by adding a boolean to check if only one incorrect character is found
     *
     * @param point
     * @param lines
     * @return
     */
    private static boolean isRowReflection2(int point, ArrayList<String> lines) {
        int reflectionLength = Math.min(lines.size() - point, point);
        boolean hasIncorrect = false;
        for (int i = 0; i < lines.getFirst().length(); i++) {
            for (int j = 1; j <= reflectionLength; j++) {
                if (lines.get(point - j).charAt(i) != lines.get(point + j - 1).charAt(i)) {
                    if (hasIncorrect) {
                        return false;
                    } else {
                        hasIncorrect = true;
                    }
                }
            }
        }

        return hasIncorrect;
    }
}