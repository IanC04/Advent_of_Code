/*
   Written by Ian Chen
   GitHub: https://github.com/IanC04
*/
package twentytwentythree;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class CubeConundrum {
    private static final int DAY = 2;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        int sum = 0;
        int i = 1;
        try (Scanner s = new Scanner(new File("2023 Input/day2_input.txt"))) {
            int[] currentColors = new int[3];
            while (s.hasNextLine()) {
                String line = s.nextLine();
                line = line.substring(line.indexOf(":") + 2);

                boolean valid = true;
                for (String set : line.split("; ")) {
                    checkColors(set, currentColors);
                    if (currentColors[0] > 12 || currentColors[1] > 13 || currentColors[2] > 14) {
                        valid = false;
                    }

                    currentColors[0] = 0;
                    currentColors[1] = 0;
                    currentColors[2] = 0;
                }

                if (valid) {
                    sum += i;
                }
                ++i;
            }
        }

        System.out.println(sum);
    }

    private static void checkColors(String set, int[] currentColors) {
        for (String color : set.split(", ")) {
            if (color.endsWith("red")) {
                currentColors[0] += Integer.parseInt(color.substring(0, color.length() - 4));
            } else if (color.endsWith("green")) {
                currentColors[1] += Integer.parseInt(color.substring(0, color.length() - 6));
            } else if (color.endsWith("blue")) {
                currentColors[2] += Integer.parseInt(color.substring(0, color.length() - 5));
            }
        }
    }

    private static void part2() throws FileNotFoundException {
        int sum = 0;
        try (Scanner s = new Scanner(new File("2023 Input/day2_input.txt"))) {
            int[] currentColors = new int[3];
            while (s.hasNextLine()) {
                String line = s.nextLine();
                line = line.substring(line.indexOf(":") + 2);

                for (String set : line.split("; ")) {
                    checkColors2(set, currentColors);
                }
                int current = currentColors[0] * currentColors[1] * currentColors[2];
                sum += current;
                currentColors[0] = 0;
                currentColors[1] = 0;
                currentColors[2] = 0;
            }
        }

        System.out.println(sum);
    }

    private static void checkColors2(String set, int[] currentMaxColors) {
        for (String color : set.split(", ")) {
            if (color.endsWith("red")) {
                currentMaxColors[0] = Math.max(Integer.parseInt(color.substring(0,
                        color.length() - 4)), currentMaxColors[0]);
            } else if (color.endsWith("green")) {
                currentMaxColors[1] = Math.max(Integer.parseInt(color.substring(0,
                        color.length() - 6)), currentMaxColors[1]);
            } else if (color.endsWith("blue")) {
                currentMaxColors[2] = Math.max(Integer.parseInt(color.substring(0,
                        color.length() - 5)), currentMaxColors[2]);
            }
        }
    }
}