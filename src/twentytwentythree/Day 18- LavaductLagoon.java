/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

class LavaductLagoon {
    private static final int DAY = 18;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        // part2();
    }

    private static enum Direction {
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

        final int dr, dc;

        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }
    }

    private record Hole(int row, int col, int RGB, char cornerType) {
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            HashMap<Integer, List<Hole>> holes = new HashMap<>();
            int[] position = new int[2];
            while (s.hasNextLine()) {
                Direction direction;
                String line = s.nextLine();
                String[] data = line.split(" ");
                data[2] = data[2].substring(2, data[2].length() - 1);
                direction = switch (data[0].charAt(0)) {
                    case 'U':
                        yield Direction.UP;
                    case 'D':
                        yield Direction.DOWN;
                    case 'L':
                        yield Direction.LEFT;
                    case 'R':
                        yield Direction.RIGHT;
                    default:
                        throw new IllegalStateException("Unexpected value: " + data[0]);
                };

                char type = direction == Direction.UP || direction == Direction.DOWN ? '|' : '-';
                for (int i = 0; i < Integer.parseInt(data[1]); ++i) {
                    position[0] += direction.dr;
                    position[1] += direction.dc;
                    holes.putIfAbsent(position[0], new ArrayList<>());
                    holes.get(position[0]).add(new Hole(position[0], position[1],
                            Integer.parseInt(data[2], 16), type));
                }
            }
            for (List<Hole> hole : holes.values()) {
                hole.sort(Comparator.comparingInt(Hole::col));
            }
            swapVertices(holes);
            displayTrench(holes);
            long area = trenchArea(holes);
            System.out.println(area);
        }
    }

    private static void swapVertices(HashMap<Integer, List<Hole>> holes) {
        int minRow = holes.keySet().stream().min(Integer::compareTo).orElseThrow();
        int maxRow = holes.keySet().stream().max(Integer::compareTo).orElseThrow();
        for (int i = minRow; i <= maxRow; ++i) {
            List<Hole> above = holes.get(i - 1);
            List<Hole> row = holes.get(i);
            List<Hole> below = holes.get(i + 1);
            for (int j = 0; j < row.size(); ++j) {
                int finalJ = j;
                boolean hasAbove = above != null && above.stream().anyMatch(hole -> hole.col() == row.get(finalJ).col());
                boolean hasBelow = below != null && below.stream().anyMatch(hole -> hole.col() == row.get(finalJ).col());
                boolean hasLeft = j > 0 && row.get(j - 1).col() == row.get(j).col() - 1;
                boolean hasRight =
                        j < row.size() - 1 && row.get(j + 1).col() == row.get(j).col() + 1;
                if (hasAbove && hasRight) {
                    row.set(j, new Hole(row.get(j).row(), row.get(j).col(), row.get(j).RGB(), 'L'));
                } else if (hasAbove && hasLeft) {
                    row.set(j, new Hole(row.get(j).row(), row.get(j).col(), row.get(j).RGB(), 'J'));
                } else if (hasBelow && hasRight) {
                    row.set(j, new Hole(row.get(j).row(), row.get(j).col(), row.get(j).RGB(), 'F'));
                } else if (hasBelow && hasLeft) {
                    row.set(j, new Hole(row.get(j).row(), row.get(j).col(), row.get(j).RGB(), '7'));
                }
            }
        }
    }

    private static void displayTrench(HashMap<Integer, List<Hole>> holes) {
        int minRow = holes.keySet().stream().min(Integer::compareTo).orElseThrow();
        int maxRow = holes.keySet().stream().max(Integer::compareTo).orElseThrow();
        int minCol = holes.values().stream().flatMap(List::stream).mapToInt(Hole::col).min().orElseThrow();
        int maxCol = holes.values().stream().flatMap(List::stream).mapToInt(Hole::col).max().orElseThrow();
        for (int i = minRow; i <= maxRow; ++i) {
            for (int j = minCol; j <= maxCol; ++j) {
                int finalJ = j;
                Hole match = holes.get(i).stream().filter(hole -> hole.col() == finalJ).findFirst().orElse(null);
                if (match != null) {
                    System.out.print(match.cornerType());
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    /**
     * Couldn't figure out how to pair up the holes to calculate the area, so I just use a scan
     * line algorithm to calculate the area.
     * @param holes
     * @return
     */
    private static long trenchArea(HashMap<Integer, List<Hole>> holes) {
        int minRow = holes.keySet().stream().min(Integer::compareTo).orElseThrow();
        int maxRow = holes.keySet().stream().max(Integer::compareTo).orElseThrow();
        int minCol = holes.values().stream().flatMap(List::stream).mapToInt(Hole::col).min().orElseThrow();
        int maxCol = holes.values().stream().flatMap(List::stream).mapToInt(Hole::col).max().orElseThrow();
        long area = 0;
        for (int i = minRow; i <= maxRow; ++i) {
            List<Hole> row = holes.get(i);

            boolean inHole = false;
            int holeIndex = 0;
            long holeArea = 0;
            char previousCorner = ' ';
            for (int j = minCol; j <= maxCol; j++) {
                while (holeIndex < row.size() && j > row.get(holeIndex).col()) {
                    ++holeIndex;
                }
                if (holeIndex >= row.size()) {
                    break;
                }
                if (row.get(holeIndex).col() == j) {
                    Hole hole = row.get(holeIndex);
                    switch (hole.cornerType()) {
                        case '|':
                            inHole = !inHole;
                            ++holeArea;
                            continue;
                        case 'F':
                        case 'L':
                            previousCorner = hole.cornerType();
                            ++holeArea;
                            continue;
                        case '7':
                            ++holeArea;
                            if (previousCorner == 'L') {
                                inHole = !inHole;
                            }
                            continue;
                        case 'J':
                            ++holeArea;
                            if (previousCorner == 'F') {
                                inHole = !inHole;
                            }
                            continue;
                        case '-':
                            ++holeArea;
                            continue;
                    }
                }
                if (inHole) {
                    ++holeArea;
                }
            }
            area += holeArea;
        }
        return area;
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                System.out.println(line);
            }
        }
    }
}