/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

class PipeMaze {
    private static final int DAY = 10;

    private static final HashMap<Character, int[][]> PIPES = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        PIPES.put('|', new int[][]{{-1, 0}, {1, 0}});
        PIPES.put('-', new int[][]{{0, -1}, {0, 1}});
        PIPES.put('L', new int[][]{{-1, 0}, {0, 1}});
        PIPES.put('J', new int[][]{{-1, 0}, {0, -1}});
        PIPES.put('7', new int[][]{{0, -1}, {1, 0}});
        PIPES.put('F', new int[][]{{0, 1}, {1, 0}});

        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<char[]> temp_maze = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                temp_maze.add(line.toCharArray());
            }
            char[][] maze = temp_maze.toArray(new char[temp_maze.size()][]);
            int[] start = findStart(maze);

            int furthest = bfs(maze, start);
            System.out.println(furthest);
        }
    }

    private static int[] findStart(char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'S') {
                    return new int[]{i, j};
                }
            }
        }

        throw new IllegalArgumentException("No start found");
    }

    private static int bfs(char[][] maze, int[] start) {
        Queue<int[]> from = new LinkedList<>();
        Queue<int[]> to = new LinkedList<>();

        getInitialPipes(maze, start, from);
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        visited[start[0]][start[1]] = true;

        int steps = 0;
        while (!from.isEmpty()) {
            int[] cur = from.poll();
            // if (visited[cur[0]][cur[1]]) {
            //     continue;
            // }

            visited[cur[0]][cur[1]] = true;

            if (PIPES.containsKey(maze[cur[0]][cur[1]]) && maze[cur[0]][cur[1]] != 'S') {
                for (int[] pipe : PIPES.get(maze[cur[0]][cur[1]])) {
                    int[] next = new int[]{cur[0] + pipe[0], cur[1] + pipe[1]};
                    if (next[0] >= 0 && next[0] < maze.length && next[1] >= 0 && next[1] < maze[0].length && !visited[next[0]][next[1]]) {
                        to.add(next);
                    }
                }
            }

            if (from.isEmpty()) {
                from = to;
                to = new LinkedList<>();
                ++steps;
            }
        }

        return steps;
    }

    private static void getInitialPipes(char[][] maze, int[] start, Queue<int[]> from) {
        for (int i = start[0] - 1; i <= start[0] + 1; i++) {
            for (int j = start[1] - 1; j <= start[1] + 1; j++) {
                if (i == start[0] && j == start[1]) {
                    continue;
                }
                if (i >= 0 && i < maze.length && j >= 0 && j < maze[0].length && maze[i][j] != ' ') {
                    if (PIPES.containsKey(maze[i][j])) {
                        for (int[] pipe : PIPES.get(maze[i][j])) {
                            if (i + pipe[0] == start[0] && j + pipe[1] == start[1]) {
                                from.add(new int[]{i, j});
                            }
                        }
                    }
                }
            }
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            ArrayList<char[]> temp_maze = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                temp_maze.add(line.toCharArray());
            }
            char[][] maze = temp_maze.toArray(new char[temp_maze.size()][]);
            int[] start = findStart(maze);
            // Hard-Coded
            maze[start[0]][start[1]] = 'J';

            markLoop(maze, start);

            // Scan line by line using regions
            int total_inside = 0;
            boolean inside = false;
            for (int i = 0; i < maze.length; i++) {
                char prev_corner = ' ';
                for (int j = 0; j < maze[i].length; j++) {
                    char c = maze[i][j];
                    switch (c) {
                        case ' ':
                            if (inside) {
                                ++total_inside;
                                maze[i][j] = '.';
                            }
                            break;
                        case '|':
                            inside = !inside;
                            break;
                        case 'F':
                        case 'L':
                            prev_corner = c;
                            break;
                        case 'J':
                        case '7':{
                            if (!isU(prev_corner, c)) {
                                inside = !inside;
                            }
                        }
                    }
                }
            }

            // for (char[] row : maze) {
            //     for (char c : row) {
            //         System.out.print(c);
            //     }
            //     System.out.println();
            // }
            System.out.println(total_inside);
        }
    }

    private static boolean isU(char prev, char cur) {
        return (prev == 'L' && cur == 'J') || (prev == 'F' && cur == '7');
    }

    private static void markLoop(char[][] maze, int[] start) {
        Queue<int[]> from = new LinkedList<>();
        getInitialPipes(maze, start, from);
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        visited[start[0]][start[1]] = true;

        while (!from.isEmpty()) {
            int[] cur = from.poll();
            // if (visited[cur[0]][cur[1]]) {
            //     continue;
            // }

            visited[cur[0]][cur[1]] = true;

            if (PIPES.containsKey(maze[cur[0]][cur[1]])) {
                for (int[] pipe : PIPES.get(maze[cur[0]][cur[1]])) {
                    int[] next = new int[]{cur[0] + pipe[0], cur[1] + pipe[1]};
                    if (next[0] >= 0 && next[0] < maze.length && next[1] >= 0 && next[1] < maze[0].length && !visited[next[0]][next[1]]) {
                        from.add(next);
                    }
                }
            }
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (!visited[i][j]) {
                    maze[i][j] = ' ';
                }
            }
        }
    }
}