package twentytwentytwo;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class TuningTrouble {
    private static final int DAY = 6;

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(firstFourPacket("2022/day" + DAY + "_input.txt"));
        System.out.println(firstFourteenPacket("2022/day" + DAY + "_input.txt"));
    }

    private static int firstFourPacket(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        s.useDelimiter("");
        int uniqueInd = 0;
        char[] chunk = new char[4];
        int charInd = 0;
        while (s.hasNext() && (uniqueInd < 4 || !allDifferent(chunk))) {
            chunk[charInd] = s.next().charAt(0);
            charInd = (charInd + 1) % chunk.length;
            uniqueInd++;
        }
        return uniqueInd;
    }

    private static boolean allDifferent(char[] block) {
        for (int i = 0; i < block.length; i++) {
            for (int j = i + 1; j < block.length; j++) {
                if (block[i] == block[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int firstFourteenPacket(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        s.useDelimiter("");
        int uniqueInd = 0;
        char[] chunk = new char[14];
        int charInd = 0;
        while (s.hasNext() && (uniqueInd < 14 || !allDifferent(chunk))) {
            chunk[charInd] = s.next().charAt(0);
            charInd = (charInd + 1) % chunk.length;
            uniqueInd++;
        }
        return uniqueInd;
    }
}