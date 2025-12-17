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
class Laboratories {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 7.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 7.txt"));
        partTwo(input);
    }

    private static void partOne(Scanner file) {
        List<char[]> lines = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            lines.add(line.toCharArray());
        }

        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i);
        }

        int[] start = null;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    start = new int[]{i, j};
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        int splits = 0;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int row = pos[0];
            int col = pos[1];
            if (grid[row][col] == '|') {
                continue;
            }

            grid[row][col] = '|';
            if (row + 1 == grid.length) {
                continue;
            }
            if (grid[row + 1][col] == '^') {
                queue.offer(new int[]{row + 1, col - 1});
                queue.offer(new int[]{row + 1, col + 1});
                splits++;
            } else {
                queue.offer(new int[]{row + 1, col});
            }
        }

        System.out.println(splits);
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

        long paths = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'S') {
                    paths = dfs(grid, i, j, new HashMap<>());
                }
            }
        }

        System.out.println(paths);
    }

    private static long dfs(char[][] grid, int row, int col, Map<Integer, Long> map) {
        if (row == grid.length) {
            return 1;
        }

        final int index = row * grid.length + col;

        if (grid[row][col] == '^') {
            if (map.containsKey(index)) {
                return map.get(index);
            }
            long paths = dfs(grid, row, col - 1, map) + dfs(grid, row, col + 1, map);
            map.put(index, paths);
            return paths;
        }
        return dfs(grid, row + 1, col, map);
    }
}