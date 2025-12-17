/**
 * @author: Ian
 * @date: 12/16/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class SecretEntrance {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 1.txt"));
        part1(input);
        input = new Scanner(new File("2025 Input/day 1.txt"));
        part2(input);
    }

    private static void part1(Scanner file) {
        final int SIZE = 100;
        int currentVal = 50;
        int zeroes = 0;

        while (file.hasNextLine()) {
            String line = file.nextLine();
            int amount = Integer.parseInt(line.substring(1));
            int sign = line.charAt(0) == 'L' ? -1 : 1;

            currentVal += sign * amount % SIZE;
            currentVal = (currentVal + SIZE) % SIZE;

            if (currentVal == 0) {
                zeroes++;
            }
        }
        System.out.println(zeroes);
    }

    private static void part2(Scanner file) {
        final int SIZE = 100;
        int currentVal = 50;
        int zeroes = 0;

        while (file.hasNextLine()) {
            String line = file.nextLine();
            int amount = Integer.parseInt(line.substring(1));
            int sign = line.charAt(0) == 'L' ? -1 : 1;

            if (sign == -1) {
                zeroes += ((100 - currentVal) % SIZE + amount) / SIZE;
            } else {
                zeroes += (currentVal + amount) / SIZE;
            }
            currentVal += sign * amount % SIZE;
            currentVal = (currentVal + SIZE) % SIZE;
        }
        System.out.println(zeroes);
    }
}