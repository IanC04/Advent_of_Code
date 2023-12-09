/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */
package twentytwentythree;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

class CamelCards {
    private static final int DAY = 7;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            // PriorityQueue<Card> deck = new PriorityQueue<>();
            // while (s.hasNext()) {
            //     deck.add(new Card(s.next(), s.nextInt()));
            // }
            List<Card> deck = new ArrayList<>();
            while (s.hasNext()) {
                deck.add(new Card(s.next(), s.nextInt()));
            }
            deck.sort(null);

            long score = 0;
            int rank = 1;
            // while (!deck.isEmpty()) {
            //     Card c = deck.poll();
            //     score += (long) rank * c.bid;
            //     ++rank;
            // }
            for (Card c : deck) {
                score += (long) rank * c.bid;
                ++rank;
            }

            System.out.println(score);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            List<Card> deck = new ArrayList<>();
            while (s.hasNext()) {
                deck.add(new Card(s.next(), s.nextInt()));
            }
            deck.sort(null);

            long score = 0;
            int rank = 1;
            for (Card c : deck) {
                score += (long) rank * c.bid;
                ++rank;
            }

            System.out.println(score);
        }
    }

    static class Card implements Comparable<Card> {
        private final String VALUES = "AKQJT98765432";
        private final String VALUES_2 = "AKQT98765432J";

        private final String name;

        private final int bid;

        private int type;

        public Card(String name, int bid) {
            this.name = name;
            this.bid = bid;

            HashMap<Character, Integer> map = new HashMap<>();
            for (char c : name.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            // Part 2
            char max = 'J';
            int count = 0, j_count;
            if (map.containsKey('J')) {
                j_count = map.remove('J');
                for (char c : map.keySet()) {
                    if (map.get(c) > count) {
                        max = c;
                        count = map.get(c);
                    } else if (map.get(c) == count) {
                        if (VALUES_2.indexOf(c) > VALUES_2.indexOf(max)) {
                            max = c;
                        }
                    }
                }
                map.put(max, map.getOrDefault(max, 0) + j_count);
            }
            // End Part 2

            type = switch (map.size()) {
                case 1 -> 7;
                case 2 -> map.containsValue(4) ? 6 : 5;
                case 3 -> map.containsValue(3) ? 4 : 3;
                case 4 -> 2;
                case 5 -> 1;
                default -> 0;
            };
        }

        @Override
        public int compareTo(Card other) {
            if (this.type != other.type) {
                return Integer.compare(this.type, other.type);
            }

            for (int i = 0; i < this.name.length(); ++i) {
                // int pos1 = VALUES.indexOf(this.name.charAt(i));
                // int pos2 = VALUES.indexOf(other.name.charAt(i));
                int pos1 = VALUES_2.indexOf(this.name.charAt(i));
                int pos2 = VALUES_2.indexOf(other.name.charAt(i));
                if (pos1 != pos2) {
                    return -Integer.compare(pos1, pos2);
                }
            }

            return 0;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
