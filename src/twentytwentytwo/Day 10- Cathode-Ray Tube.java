package twentytwentytwo;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class CathodeRayTube {
    private static final int DAY = 10;

    private enum Operations {
        noop, addx
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(signalStrengthsBounded("2022/day" + DAY + "_input.txt"));
        char[][] screen = createCRTScreen("2022/day" + DAY + "_input.txt");
        for (char[] line : screen) {
            System.out.println(Arrays.toString(line));
        }
    }

    private static int signalStrengthsBounded(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        int signalStrength = 1, strengthSum = 0, cycle = 1;
        while (s.hasNext()) {
            if ((cycle - 20) % 40 == 0) {
                strengthSum += cycle * signalStrength;
                System.out.println(strengthSum + " " + cycle + " " + signalStrength);
            }
            switch (Operations.valueOf(s.next())) {
                case addx -> {
                    cycle++;
                    if ((cycle - 20) % 40 == 0) {
                        strengthSum += cycle * signalStrength;
                        System.out.println(strengthSum + " " + cycle + " " + signalStrength);
                    }
                    signalStrength += s.nextInt();
                }
            }
            cycle++;
        }
        return strengthSum;
    }

    private static char[][] createCRTScreen(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        char[][] screen = new char[6][40];
        int signalStrength = 1, cycle = 0;
        while (s.hasNext()) {
            screen[cycle / 40][cycle % 40] = Math.abs(signalStrength - cycle % 40) <= 1 ? '#' :
                    '.';
            switch (Operations.valueOf(s.next())) {
                case addx -> {
                    cycle++;
                    screen[cycle / 40][cycle % 40] = Math.abs(signalStrength - cycle % 40) <= 1 ?
                            '#' :
                            '.';
                    signalStrength += s.nextInt();
                }
            }
            System.out.println(signalStrength + " " + cycle);
            cycle++;
        }
        return screen;
    }
}