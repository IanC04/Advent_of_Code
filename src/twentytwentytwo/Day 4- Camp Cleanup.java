package twentytwentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class CampCleanup {
    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(numContains("2022 Input/day4_input.txt"));
        System.out.println(numOverlap("2022 Input/day4_input.txt"));
    }

    private static int numContains(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        int sum = 0;
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split(",");
            int firstStart = Integer.parseInt(line[0].substring(0, line[0].indexOf('-'))),
                    firstEnd = Integer.parseInt(line[0].substring(line[0].indexOf('-') + 1)),
                    secondStart = Integer.parseInt(line[1].substring(0, line[1].indexOf('-'))),
                    secondEnd = Integer.parseInt(line[1].substring(line[1].indexOf('-') + 1));
            if (firstStart <= secondStart && firstEnd >= secondEnd || secondStart <= firstStart && secondEnd >= firstEnd) {
                sum++;
            }
        }
        return sum;
    }

    private static int numOverlap(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        int sum = 0;
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split(",");
            int firstStart = Integer.parseInt(line[0].substring(0, line[0].indexOf('-'))),
                    firstEnd = Integer.parseInt(line[0].substring(line[0].indexOf('-') + 1)),
                    secondStart = Integer.parseInt(line[1].substring(0, line[1].indexOf('-'))),
                    secondEnd = Integer.parseInt(line[1].substring(line[1].indexOf('-') + 1));
            if (firstStart <= secondStart && firstEnd >= secondStart || secondStart <= firstStart && secondEnd >= firstStart) {
                sum++;
            }
        }
        return sum;
    }
}
