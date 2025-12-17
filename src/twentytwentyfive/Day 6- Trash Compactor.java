/**
 * @author: Ian
 * @date: 12/17/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class TrashCompactor {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 6.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 6.txt"));
        partTwo(input);
    }

    private static void partOne(Scanner file) {
        List<List<Long>> operands = new ArrayList<>();

        char[] operations = new char[1];
        while (file.hasNextLine()) {
            String line = file.nextLine().trim();
            if (line.contains("+") || line.contains("*")) {
                operations = line.replace(" ", "").toCharArray();
                break;
            }

            long[] nums = Arrays.stream(line.split(" +")).mapToLong(Long::parseLong).toArray();
            if (operands.isEmpty()) {
                for (long i : nums) {
                    operands.add(new ArrayList<>());
                }
            }

            int index = 0;
            for (long i : nums) {
                operands.get(index++).add(i);
            }
        }

        long total = 0;
        for (int i = 0; i < operations.length; i++) {
            total += process(operands.get(i), operations[i]);
        }

        System.out.println(total);
    }

    private static long process(List<Long> c, char operation) {
        return switch (operation) {
            case '+' -> c.stream().reduce(0L, Long::sum);
            case '*' -> c.stream().reduce(1L, (a, b) -> a * b);
            default -> throw new IllegalArgumentException();
        };
    }

    private static void partTwo(Scanner file) {
        List<char[]> lines = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            lines.add(line.toCharArray());
        }

        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i);
        }

        long result = 0;
        List<Long> operands = new LinkedList<>();
        char operation = '+';
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[grid.length - 1][j] == '*' || grid[grid.length - 1][j] == '+') {
                result += process(operands, operation);
                operands.clear();

                operation = grid[grid.length - 1][j];
            }

            long operand = 0;
            for (int i = 0; i < grid.length - 1; i++) {
                if (grid[i][j] == ' ') {
                    continue;
                }
                operand *= 10;
                operand += grid[i][j] - '0';
            }
            if (operand != 0) {
                operands.add(operand);
            }
        }
        result += process(operands, operation);

        System.out.println(result);
    }
}