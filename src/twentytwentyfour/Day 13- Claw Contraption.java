/**
 * @author: Ian Chen
 * @date: 1/1/2025
 */

package twentytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class ClawContraption {

    public static void main(String[] args) throws IOException {
        String lines = String.join("\n", Files.readAllLines(Path.of("2024 Input/day13_input.txt")));
        part1(lines);
        part2(lines);
    }

    private static void part1(String lines) {
        Scanner scanner = new Scanner(lines);
        scanner.useDelimiter("\\D+");
        int tokens = 0;
        while (scanner.hasNextLine()) {
            int x1 = scanner.nextInt();
            int y1 = scanner.nextInt();
            int x2 = scanner.nextInt();
            int y2 = scanner.nextInt();
            int X = scanner.nextInt();
            int Y = scanner.nextInt();
            long[] solutions = solve(x1, x2, y1, y2, X, Y);
            if (solutions != null) {
                tokens += (int) (solutions[0] * 3 + solutions[1]);
            }
        }
        System.out.println(tokens);
    }

    private static void part2(String lines) {
        Scanner scanner = new Scanner(lines);
        scanner.useDelimiter("\\D+");
        long tokens = 0;
        while (scanner.hasNextLine()) {
            long x1 = scanner.nextInt();
            long y1 = scanner.nextInt();
            long x2 = scanner.nextInt();
            long y2 = scanner.nextInt();
            long X = scanner.nextInt() + 10_000_000_000_000L;
            long Y = scanner.nextInt() + 10_000_000_000_000L;
            long[] solutions = solve(x1, x2, y1, y2, X, Y);
            if (solutions != null) {
                tokens += solutions[0] * 3 + solutions[1];
            }
        }
        System.out.println(tokens);
    }

    private static long[] solve(long a, long b, long c, long d, long X, long Y) {
        long determinant = a * d - b * c;
        if (determinant == 0) {
            return null;
        }
        long amountA = d * X - b * Y;
        long amountB = -c * X + a * Y;
        if (amountA % determinant != 0 || amountB % determinant != 0) {
            return null;
        }
        return new long[]{amountA / determinant, amountB / determinant};
    }
}