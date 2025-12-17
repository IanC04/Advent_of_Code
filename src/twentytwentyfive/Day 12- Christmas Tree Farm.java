/**
 * @author: Ian
 * @date: 12/18/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class ChristmasTreeFarm {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 12.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 12.txt"));
        // partTwo(input);
    }

    private static void partOne(Scanner file) {
        while (file.hasNextLine()) {
            String line = file.nextLine();
            System.out.println(line);
            // TODO
        }
    }

    private static void partTwo(Scanner file) {
        while (file.hasNextLine()) {
            String line = file.nextLine();
            System.out.println(line);
            // TODO
        }
    }
}