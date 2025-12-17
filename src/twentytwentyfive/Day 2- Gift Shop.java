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
class GiftShop {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 2.txt"));
        part1(input);
        input = new Scanner(new File("2025 Input/day 2.txt"));
        part2(input);
    }

    private static void part1(Scanner file) {
        while (file.hasNextLine()) {
            String line = file.nextLine();
            String[] products = line.split(",");
            long sum = 0;
            for (String product : products) {
                String startStr = product.substring(0, product.indexOf("-"));
                String endStr = product.substring(product.indexOf("-") + 1);
                long start = Long.parseLong(startStr);
                long end = Long.parseLong(endStr);

                for (long i = start; i <= end; i++) {
                    String iStr = "" + i;
                    if (iStr.length() % 2 == 0 &&
                            iStr.substring(0, iStr.length() / 2).equals(
                                    iStr.substring(iStr.length() / 2))) {
                        sum += i;
                    }
                }
            }
            System.out.println(sum);
        }
    }

    private static void part2(Scanner file) {
        while (file.hasNextLine()) {
            String line = file.nextLine();
            String[] products = line.split(",");
            long sum = 0;
            for (String product : products) {
                String startStr = product.substring(0, product.indexOf("-"));
                String endStr = product.substring(product.indexOf("-") + 1);
                long start = Long.parseLong(startStr);
                long end = Long.parseLong(endStr);

                for (long i = start; i <= end; i++) {
                    String iStr = "" + i;
                    for (int j = 1; j <= iStr.length() / 2; j++) {
                        if (iStr.length() % j == 0) {
                            if (iStr.substring(0, j).repeat(iStr.length() / j).equals(iStr)) {
                                sum += i;
                                break;
                            }
                        }
                    }
                }
            }
            System.out.println(sum);
        }
    }
}