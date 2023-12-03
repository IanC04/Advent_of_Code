package twentytwentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

class RockPaperScissors {

    private static HashMap<Character, Integer> point;

    private static final int A_TO_X = 23;

    static {
        point = new HashMap<>();
        point.put('X', 1);
        point.put('Y', 2);
        point.put('Z', 3);
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(maxScorePart1("2022 Input/day2_input.txt"));
        System.out.println(maxScorePart2("2022 Input/day2_input.txt"));
    }

    private static int maxScorePart1(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        int score = 0;
        while (s.hasNext()) {
            char opp = s.next().charAt(0), you = s.next().charAt(0);
            score += point.get(you);
            opp += A_TO_X;
            if (opp == you) {
                score += 3;
            } else if ((opp == 'X' && you == 'Y') || (opp == 'Y' && you == 'Z') ||
                    (opp == 'Z' && you == 'X')) {
                score += 6;
            }
        }
        return score;
    }

    private static int maxScorePart2(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        int score = 0;
        while (s.hasNext()) {
            char opp = s.next().charAt(0), you = s.next().charAt(0);
            opp += A_TO_X;
            if (you == 'Y') {
                score += 3;
            } else if (you == 'Z') {
                score += 6;
                opp += 1;
            }
            else {
                opp -= 1;
            }
            if (opp < 'X') {
                opp = 'Z';
            } else if (opp > 'Z') {
                opp = 'X';
            }
            score += point.get(opp);
        }
        return score;
    }
}
