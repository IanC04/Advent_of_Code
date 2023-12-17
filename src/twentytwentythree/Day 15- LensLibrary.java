/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

class LensLibrary {
    private static final int DAY = 15;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            assert s.hasNextLine();
            String line = s.nextLine();
            String[] data = line.split(",");
            long sum = 0;
            for (String d : data) {
                long hashVal = getHash(d);
                // System.out.println(hashVal);
                sum += hashVal;
            }

            System.out.println(sum);
        }
    }

    private static long getHash(String data) {
        long hash = 0;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash + data.charAt(i)) * 17 % 256;
        }
        return hash;
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            assert s.hasNextLine();
            String line = s.nextLine();
            String[] data = line.split(",");
            List<Lens>[] hashTable = new List[256];
            Arrays.setAll(hashTable, element -> new ArrayList<>());
            for (String d : data) {
                boolean assignment = d.contains("=");
                if (assignment) {
                    Lens lens = new Lens(d.substring(0, d.indexOf('=')), Integer.parseInt(d.substring(d.indexOf('=') + 1)));
                    int hash = lens.hashCode();
                    if (hashTable[hash].contains(lens)) {
                        hashTable[hash].get(hashTable[hash].indexOf(lens)).focalLength = lens.focalLength;
                    } else {
                        hashTable[hash].add(lens);
                    }
                } else {
                    String label = d.substring(0, d.indexOf('-'));
                    int hash = (int) getHash(label);
                    for (int i = 0; i < hashTable[hash].size(); i++) {
                        if (hashTable[hash].get(i).label.equals(label)) {
                            hashTable[hash].remove(i);
                            break;
                        }
                    }
                }
            }

            long sum = 0;
            for (int i = 0; i < hashTable.length; i++) {
                if (hashTable[i].isEmpty()) {
                    continue;
                }
                for (int j = 0; j < hashTable[i].size(); j++) {
                    sum += (long) (i + 1) * (j + 1) * hashTable[i].get(j).focalLength;
                }
            }

            System.out.println(sum);
        }
    }

    private static class Lens {
        private String label;

        private int focalLength;

        private Lens(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }

        @Override
        public int hashCode() {
            return (int) getHash(label);
        }

        @Override
        public boolean equals(Object other) {
            assert other instanceof Lens;
            return label.equals(((Lens) other).label);
        }
    }
}