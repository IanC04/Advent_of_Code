/**
 * @author: Ian
 * @date: 12/18/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class Factory {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 10.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 10.txt"));
        partTwo(input);
    }

    private static void partOne(Scanner file) {
        long presses = 0;
        while (file.hasNextLine()) {
            String[] line = file.nextLine().split(" ");
            line = Arrays.copyOf(line, line.length - 1);

            for (int i = 0; i < line.length; i++) {
                line[i] = line[i].substring(1, line[i].length() - 1);
            }

            int state = 0;
            for (int i = 0; i < line[0].length(); i++) {
                if (line[0].charAt(i) == '#') {
                    state += 1 << i;
                }
            }

            List<Integer> operations = new ArrayList<>();
            for (int i = 1; i < line.length; i++) {
                String[] indices = line[i].split(",");
                int operation = 0;
                for (String index : indices) {
                    operation += 1 << Integer.parseInt(index);
                }

                operations.add(operation);
            }

            presses += dfs(operations, state, 0, 0);
        }

        System.out.println(presses);
    }

    private static int dfs(List<Integer> operations, int state, int index, int size) {
        if (state == 0) {
            return size;
        }
        if (index == operations.size()) {
            return Integer.MAX_VALUE;
        }

        return Math.min(
                dfs(operations, state ^ operations.get(index), index + 1, size + 1),
                dfs(operations, state, index + 1, size));
    }

    private static void partTwo(Scanner file) {
        long presses = 0;
        while (file.hasNextLine()) {
            String[] line = file.nextLine().split(" ");

            for (int i = 0; i < line.length; i++) {
                line[i] = line[i].substring(1, line[i].length() - 1);
            }

            int[] counter = Arrays.stream(line[line.length - 1].split(",")).mapToInt(Integer::parseInt).toArray();
            int state = 0;
            for (int i = 0; i < counter.length; i++) {
                if (counter[i] % 2 == 1) {
                    state += 1 << i;
                }
            }

            List<Operation> operations = new ArrayList<>();
            for (int i = 1; i < line.length - 1; i++) {
                int[] indices =
                        Arrays.stream(line[i].split(",")).mapToInt(Integer::parseInt).toArray();
                int operation = 0;
                for (int index : indices) {
                    operation += 1 << index;
                }

                operations.add(new Operation(operation, indices));
            }

            presses += dfsTwo(operations, counter, state, 0, 0);
        }

        System.out.println(presses);
    }

    private record Operation(int op, int[] index) {
    }

    private static int dfsTwo(List<Operation> operations,
                              int[] requirements,
                              int state,
                              int index,
                              int size) {
        if (index == operations.size()) {
            if (state == 0) {
                if (Arrays.stream(requirements).anyMatch(r -> r < 0)) {
                    return Integer.MAX_VALUE;
                }
                if (Arrays.stream(requirements).allMatch(r -> r == 0)) {
                    return size;
                }

                for (int i = 0; i < requirements.length; i++) {
                    requirements[i] /= 2;
                    state += requirements[i] % 2 << i;
                }

                int result = dfsTwo(operations, requirements, state, 0, 0);
                for (int i = 0; i < requirements.length; i++) {
                    requirements[i] *= 2;
                }

                if (result != Integer.MAX_VALUE) {
                    return size + 2 * result;
                }
            }

            return Integer.MAX_VALUE;
        }

        int min = Integer.MAX_VALUE;
        min = Math.min(min,
                dfsTwo(operations, requirements, state, index + 1, size));

        for (int i : operations.get(index).index) {
            requirements[i]--;
        }
        min = Math.min(min,
                dfsTwo(operations, requirements, state ^ operations.get(index).op, index + 1,
                        size + 1));
        for (int i : operations.get(index).index) {
            requirements[i]++;
        }

        return min;
    }
}