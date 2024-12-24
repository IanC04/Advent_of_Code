/**
 * @author: Ian Chen
 * @date: 12/30/2024
 */

package twentytwentyfour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class PlutonianPebbles {
    private static final Map<Pair, Long> CACHE = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String lines = String.join("\n", Files.readAllLines(Path.of("2024 Input/day11_input.txt")));
        part1(lines);
        part2(lines);
    }

    private static void part1(String lines) {
        List<Long> stones = new ArrayList<>(Arrays.stream(lines.split(" ")).mapToLong(Long::parseLong).boxed().toList());
        long size = 0;
        for (long stone : stones) {
            size += blinkStone(stone, 25);
        }
        System.out.println(size);
    }

    private static void part2(String lines) {
        List<Long> stones = new ArrayList<>(Arrays.stream(lines.split(" ")).mapToLong(Long::parseLong).boxed().toList());
        long size = 0;
        for (long stone : stones) {
            size += blinkStone(stone, 75);
        }
        System.out.println(size);
    }

    private record Pair(long val, int blinks) {
    }

    private static long blinkStone(long stone, int times) {
        if (times == 0) {
            return 1;
        }
        if (CACHE.containsKey(new Pair(stone, times))) {
            return CACHE.get(new Pair(stone, times));
        }
        long length;
        if (stone == 0) {
            length = blinkStone(1, times - 1);
        } else {
            String val = String.valueOf(stone);
            if (val.length() % 2 == 0) {
                length = blinkStone(Long.parseLong(val.substring(0, val.length() / 2)), times - 1) +
                        blinkStone(Long.parseLong(val.substring(val.length() / 2)), times - 1);
            } else {
                length = blinkStone(stone * 2024, times - 1);
            }
        }
        CACHE.put(new Pair(stone, times), length);
        return length;
    }
}