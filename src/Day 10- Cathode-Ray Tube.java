import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class CathodeRayTube {
    private static final int DAY = 10;

    public static void main(String[] args) throws FileNotFoundException {
        signalStrengthsBounded("day" + DAY + "_input.txt");
    }

    private static void signalStrengthsBounded(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
    }
}