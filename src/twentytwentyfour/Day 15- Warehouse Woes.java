/**
 * @author: Ian Chen
 * @date: 1/2/2025
 */

package twentytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class WarehouseWoes {

    private static Map<Character, int[]> DIRECTIONS = Map.of(
            '^', new int[]{-1, 0},
            '>', new int[]{0, 1},
            'v', new int[]{1, 0},
            '<', new int[]{0, -1});

    public static void main(String[] args) throws IOException {
        String lines = String.join("\n", Files.readAllLines(Path.of("2024 Input/day15_input.txt")));
        part1(lines);
        part2(lines);
    }

    private record Position(int row, int col) {
    }

    private static void part1(String lines) {
        Scanner scanner = new Scanner(lines);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }
            list.add(line);
        }
        char[][] grid = list.stream().map(String::toCharArray).toArray(char[][]::new);
        String movement = scanner.tokens().reduce("", String::concat);
        Position position = new Position(-1, -1);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '@') {
                    position = new Position(i, j);
                }
            }
        }
        for (char move : movement.toCharArray()) {
            position = move(grid, position, DIRECTIONS.get(move));
        }
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'O') {
                    sum += 100 * i + j;
                }
            }
        }
        System.out.println(sum);
    }

    private static void part2(String lines) {
        Scanner scanner = new Scanner(lines);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }
            list.add(line);
        }
        char[][] grid = new char[list.size()][list.getFirst().length() * 2];
        Position position = new Position(-1, -1);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length(); j++) {
                switch (list.get(i).charAt(j)) {
                    case '#' -> {
                        grid[i][2 * j] = '#';
                        grid[i][2 * j + 1] = '#';
                    }
                    case 'O' -> {
                        grid[i][2 * j] = '[';
                        grid[i][2 * j + 1] = ']';
                    }
                    case '.' -> {
                        grid[i][2 * j] = '.';
                        grid[i][2 * j + 1] = '.';
                    }
                    case '@' -> {
                        grid[i][2 * j] = '@';
                        grid[i][2 * j + 1] = '.';
                        position = new Position(i, 2 * j);
                    }
                }
            }
        }
        String movement = scanner.tokens().reduce("", String::concat);
        for (char move : movement.toCharArray()) {
            int[] dir = DIRECTIONS.get(move);
            if (canMove2(grid, position, dir)) {
                move2(grid, position, dir);
                grid[position.row][position.col] = '.';
                position = new Position(position.row + dir[0], position.col + dir[1]);
                grid[position.row][position.col] = '@';
            }
        }
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '[') {
                    sum += 100 * i + j;
                }
            }
        }
        System.out.println(sum);
    }

    private static Position move(char[][] grid, Position pos, int[] direction) {
        Position newPos = new Position(pos.row + direction[0], pos.col + direction[1]);
        int r = newPos.row, c = newPos.col;
        while (grid[r][c] == 'O') {
            r += direction[0];
            c += direction[1];
        }
        if (grid[r][c] == '#') {
            return pos;
        }
        grid[r][c] = 'O';
        grid[pos.row][pos.col] = '.';
        grid[newPos.row][newPos.col] = '@';
        return newPos;
    }

    private static boolean canMove2(char[][] grid, Position pos, int[] direction) {
        if (direction[0] != 0) {
            int r = pos.row + direction[0];
            if (grid[r][pos.col] == '#') {
                return false;
            }
            if (grid[r][pos.col] == '.') {
                return true;
            }
            boolean aboveClear = canMove2(grid, new Position(r, pos.col), direction);
            if (grid[r][pos.col] == '[') {
                return aboveClear && canMove2(grid, new Position(r, pos.col + 1), direction);
            }
            return aboveClear && canMove2(grid, new Position(r, pos.col - 1), direction);
        }
        for (int j = pos.col; 0 <= j && j < grid[0].length; j += direction[1]) {
            if (grid[pos.row][j] == '#') {
                return false;
            }
            if (grid[pos.row][j] == '.') {
                return true;
            }
        }
        throw new IllegalStateException();
    }

    private static void move2(char[][] grid, Position pos, int[] direction) {
        if (direction[0] != 0) {
            int r = pos.row + direction[0];
            char side = grid[r][pos.col];
            if (side == '[' || side == ']') {
                int offset = side == '[' ? 1 : -1;
                move2(grid, new Position(r, pos.col), direction);
                move2(grid, new Position(r, pos.col + offset), direction);
                grid[pos.row + 2 * direction[0]][pos.col] = grid[pos.row + direction[0]][pos.col];
                grid[pos.row + 2 * direction[0]][pos.col + offset] = grid[pos.row + direction[0]][pos.col + offset];
                grid[pos.row + direction[0]][pos.col] = '.';
                grid[pos.row + direction[0]][pos.col + offset] = '.';
            }
        } else if (grid[pos.row][pos.col] != '.') {
            move2(grid, new Position(pos.row, pos.col + direction[1]), direction);
            grid[pos.row][pos.col + direction[1]] = grid[pos.row][pos.col];
        }
    }
}