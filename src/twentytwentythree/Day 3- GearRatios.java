/*
   Written by Ian Chen
   GitHub: https://github.com/IanC04
*/
package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

class GearRatios {
    private static final int DAY = 3;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();

            int sum = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); ++i) {
                int number = 0;
                boolean hasSymbol = false;

                for (int j = 0; j < lines.get(i).length(); ++j) {
                    char c = lines.get(i).charAt(j);
                    if (Character.isDigit(c)) {
                        number *= 10;
                        number += (c - '0');
                        hasSymbol = hasSymbol | symbolSurround(lines, i, j);
                    } else {
                        if (hasSymbol) {
                            // System.out.println(number);
                            sum += number;
                        }
                        number = 0;
                        hasSymbol = false;
                    }
                    if (j == lines.get(i).length() - 1) {
                        // System.out.println(number);
                        if (hasSymbol) {
                            sum += number;
                        }
                    }
                }
            }

            System.out.println(sum);
        }
    }

    private static boolean symbolSurround(ArrayList<String> lines, int i, int j) {
        boolean hasSymbol = false;
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k >= 0 && k < lines.size() && l >= 0 && l < lines.get(k).length()) {
                    char c = lines.get(k).charAt(l);
                    if (!Character.isDigit(c) && c != '.') {
                        hasSymbol = true;
                    }
                }
            }
        }

        return hasSymbol;
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();

            long sum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(i).length(); j++) {
                    char c = lines.get(i).charAt(j);
                    if (c == '*') {
                        int total = 0;

                        int[] numbers = new int[2];
                        for (int k = i - 1; k <= i + 1; k++) {
                            for (int l = j - 1; l <= j + 1; l++) {
                                if (k >= 0 && k < lines.size() && l >= 0 && l < lines.get(k).length()) {
                                    char c2 = lines.get(k).charAt(l);
                                    boolean leftIsDigit = false;
                                    if (k != i && l != j - 1 && l > 0) {
                                        leftIsDigit = Character.isDigit(lines.get(k).charAt(l - 1));
                                    }
                                    if (Character.isDigit(c2) && !leftIsDigit) {
                                        ++total;
                                        if (total <= 2) {
                                            int number = getNumber(lines, k, l);
                                            numbers[total - 1] = number;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (total == 2) {
                            sum += (long) numbers[0] * numbers[1];
                        }
                    }
                }
            }
            System.out.println(sum);
        }
    }

    private static int getNumber(ArrayList<String> lines, int i, int j) {
        int number = 0;
        while (j >= 0 && Character.isDigit(lines.get(i).charAt(j))) {
            --j;
        }

        for (int k = j + 1; k < lines.get(i).length(); ++k) {
            char c = lines.get(i).charAt(k);
            if (Character.isDigit(c)) {
                number *= 10;
                number += (c - '0');
            } else {
                break;
            }
        }

        return number;
    }
}