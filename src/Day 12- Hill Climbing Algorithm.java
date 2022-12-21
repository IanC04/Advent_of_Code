import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class HillClimbingAlgorithm {
    private static final int DAY = 12;

    public static void main(String[] args) throws FileNotFoundException {
        shortestPathLength("day" + DAY + "_input.txt");
    }

    private static void shortestPathLength(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);

    }
}