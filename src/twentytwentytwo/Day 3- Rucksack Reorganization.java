package twentytwentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class RucksackReorganization {

    private static int UPPERCASE = 38;

    private static int LOWERCASE = 96;

    private static HashSet<Character> alreadyFound;
    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(maxScorePart1("2022/day3_input.txt"));
        System.out.println(maxScorePart2("2022/day3_input.txt"));
    }

    private static int maxScorePart1(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        int sum = 0;
        while (s.hasNextLine()) {
            alreadyFound = new HashSet<>();
            sum += getPriority(s.nextLine());
        }
        return sum;
    }

    private static int getPriority(String nextLine) {
        String comp1 = nextLine.substring(0, nextLine.length() >> 1), comp2 = nextLine.substring(
                nextLine.length() >> 1);
        int sum = 0;
        for (int i = 0; i < comp1.length(); i++) {
            if (comp2.indexOf(comp1.charAt(i)) != -1 && !alreadyFound.contains(comp1.charAt(i))) {
                alreadyFound.add(comp1.charAt(i));
                if (Character.isUpperCase(comp1.charAt(i))) {
                    sum += comp1.charAt(i) - UPPERCASE;
                }
                else {
                    sum += comp1.charAt(i) - LOWERCASE;
                }
            }
        }
        return sum;
    }

    private static int maxScorePart2(String fileName) throws FileNotFoundException{
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        int sum = 0;
        while (s.hasNextLine()) {
            alreadyFound = new HashSet<>();
            sum += getPriority(s.nextLine(), s.nextLine(), s.nextLine());
        }
        return sum;
    }

    private static int getPriority(String p1, String p2, String p3) {
        for (int i = 0; i < p1.length(); i++) {
            if (p2.indexOf(p1.charAt(i)) != -1 && p3.indexOf(p1.charAt(i)) != -1) {
                if (Character.isUpperCase(p1.charAt(i))) {
                    return p1.charAt(i) - UPPERCASE;
                }
                else {
                    return p1.charAt(i) - LOWERCASE;
                }
            }
        }
        return 0;
    }
}
