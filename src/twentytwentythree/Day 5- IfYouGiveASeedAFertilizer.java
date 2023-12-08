/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */
package twentytwentythree;

import java.io.File;
import java.sql.Array;
import java.util.*;
import java.io.FileNotFoundException;

class IfYouGiveASeedAFertilizer {
    private static final int DAY = 5;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            long[] seeds =
                    Arrays.stream(s.nextLine().substring(7).split(" ")).map(Long::parseLong).mapToLong(i -> i).toArray();
            boolean[] changed = new boolean[seeds.length];

            while (s.hasNextLine()) {
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    if (line.contains("map")) {
                        break;
                    }
                }

                while (s.hasNextLong()) {
                    long dest = s.nextLong();
                    long src = s.nextLong();
                    long range = s.nextLong();

                    for (int i = 0; i < seeds.length; i++) {
                        if (!changed[i] && seeds[i] >= src && seeds[i] < src + range) {
                            seeds[i] += dest - src;
                            changed[i] = true;
                        }
                    }
                }

                changed = new boolean[seeds.length];
                // System.out.println(Arrays.toString(seeds));
            }

            System.out.println(Arrays.stream(seeds).min());
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            List<SeedRange> old_seeds = new ArrayList<>();
            List<SeedRange> new_seeds = new ArrayList<>();
            while (s.hasNextLine()) {
                s.next();
                while (s.hasNextLong()) {
                    old_seeds.add(new SeedRange(s.nextLong(), s.nextLong()));
                }

                while (s.hasNextLine()) {
                    while (s.hasNextLine()) {
                        String line = s.nextLine();
                        if (line.contains("map")) {
                            break;
                        }
                    }

                    while (s.hasNextLong()) {
                        long dest = s.nextLong();
                        long src = s.nextLong();
                        long range = s.nextLong();

                        for (int i = old_seeds.size() - 1; i >= 0; --i) {
                            SeedRange seed = old_seeds.get(i);
                            if (seed.start + seed.range > src && seed.start < src + range) {
                                seed.mapSeeds(dest, src, range, new_seeds, old_seeds);
                            }

                            if (seed.isEmpty()) {
                                old_seeds.remove(i);
                            }
                        }
                    }
                    new_seeds.addAll(old_seeds);
                    old_seeds.clear();
                    List<SeedRange> temp = old_seeds;
                    old_seeds = new_seeds;
                    new_seeds = temp;

                    // System.out.println(old_seeds);
                }

                System.out.println(old_seeds.stream().min(SeedRange::compareTo));
            }
        }
    }

    private static class SeedRange implements Comparable<SeedRange> {
        private final long start;
        private final long range;

        private boolean used = false;

        public SeedRange(long start, long range) {
            this.start = start;
            this.range = range;
        }

        public void mapSeeds(long dest, long src, long range, List<SeedRange> new_seeds,
                             List<SeedRange> old_seeds) {
            long new_start = Math.max(this.start, src);
            long new_end = Math.min(this.start + this.range, src + range);

            new_seeds.add(new SeedRange((dest - src) + new_start, new_end - new_start));

            if (this.start < src) {
                old_seeds.add(new SeedRange(this.start, src - this.start));
            }
            if (this.start + this.range > src + range) {
                old_seeds.add(new SeedRange(src + range, (this.start + this.range) - (src + range)));
            }

            // this.start = Long.MAX_VALUE;
            // this.range = -1;
            this.used = true;
        }

        public boolean isEmpty() {
            return this.used/*range <= 0*/;
        }

        public int compareTo(SeedRange other) {
            return Long.compare(this.start, other.start);
        }

        public String toString() {
            return start + " " + range;
        }
    }
}