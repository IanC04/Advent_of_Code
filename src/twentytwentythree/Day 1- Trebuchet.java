/*
   Written by Ian Chen
   GitHub: https://github.com/IanC04
*/

package twentytwentythree;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileNotFoundException;

class Trebuchet {
    private static final int DAY = 1;

    private static final HashMap<String, Integer> NUMBERS = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        NUMBERS.put("zero", 0);
        NUMBERS.put("one", 1);
        NUMBERS.put("two", 2);
        NUMBERS.put("three", 3);
        NUMBERS.put("four", 4);
        NUMBERS.put("five", 5);
        NUMBERS.put("six", 6);
        NUMBERS.put("seven", 7);
        NUMBERS.put("eight", 8);
        NUMBERS.put("nine", 9);

        System.out.println(sumFirstAndLastDigits());
    }

    private static int sumFirstAndLastDigits() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day1_input.txt"))) {
            int sum = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine();
                int first = getDigit(line, true);
                int last = getDigit(line, false);
                // System.out.println(first + " " + last);

                sum += first * 10 + last;
            }

            return sum;
        }
    }

    private static int getDigit(String line, boolean first) {
        if (first) {
            for (int i = 0; i < line.length(); ++i) {
                if (Character.isDigit(line.charAt(i))) {
                    return Integer.parseInt(line.substring(i, i + 1));
                }

                for (String key : NUMBERS.keySet()) {
                    if (line.startsWith(key, i)) {
                        int digit = NUMBERS.get(key);
                        return digit;
                    }
                }
            }
        } else {
            for (int i = line.length() - 1; i >= 0; --i) {
                if (Character.isDigit(line.charAt(i))) {
                    return Integer.parseInt(line.substring(i, i + 1));
                }

                for (String key : NUMBERS.keySet()) {
                    if (line.startsWith(key, i)) {
                        int digit = NUMBERS.get(key);
                        return digit;
                    }
                }
            }
        }

        throw new IllegalArgumentException("No digit found");
    }
}