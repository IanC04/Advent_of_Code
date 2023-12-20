/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.math.BigInteger;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.stream.IntStream;

class Aplenty {
    private static final int DAY = 19;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    /**
     * Skewed tree structure with conditions at nodes and instructions at leaves
     */
    private static class Instruction {
        final String name;
        Condition condition;

        Instruction left, right;

        private Instruction(String name, HashMap<String, Instruction> map) {
            this.name = name;
            assert !map.containsKey(name);
            map.put(name, this);
        }

        private Instruction(String name, String data, HashMap<String, Instruction> map) {
            this(name, map);
            modify(data, map);
        }

        private void modify(String data, HashMap<String, Instruction> map) {
            String truePart = data.substring(0, data.indexOf(','));
            this.condition = new Condition(truePart.substring(0, truePart.indexOf(':')));
            String trueInstructionName = truePart.substring(truePart.indexOf(':') + 1);
            if (map.containsKey(trueInstructionName)) {
                this.left = map.get(trueInstructionName);
            } else {
                this.left = new Instruction(trueInstructionName, map);
            }

            String falsePart = data.substring(data.indexOf(',') + 1);
            if (falsePart.contains(":")) {
                this.right = new Instruction("N/A", falsePart, map);
            } else {
                if (map.containsKey(falsePart)) {
                    this.right = map.get(falsePart);
                } else {
                    this.right = new Instruction(falsePart, map);
                }
            }
        }

        private boolean test(Sample sample) {
            if (condition == null) {
                System.out.println(name);
                return name.equals("A");
            }
            System.out.print(name + "-> ");
            boolean matches = condition.matches(sample);
            return matches ? left.test(sample) : right.test(sample);
        }
    }

    private static class Condition {
        final char variable, relation;

        final int bound;

        Condition(String expression) {
            this.variable = expression.charAt(0);
            this.relation = expression.charAt(1);
            this.bound = Integer.parseInt(expression.substring(2));
        }

        public boolean matches(Sample sample) {
            return switch (variable) {
                case 'x' -> switch (relation) {
                    case '>' -> sample.x > bound;
                    case '<' -> sample.x < bound;
                    // case '=' -> sample.x == bound;
                    default -> throw new IllegalStateException("Unexpected value: " + relation);
                };
                case 'm' -> switch (relation) {
                    case '>' -> sample.m > bound;
                    case '<' -> sample.m < bound;
                    // case '=' -> sample.m == bound;
                    default -> throw new IllegalStateException("Unexpected value: " + relation);
                };
                case 'a' -> switch (relation) {
                    case '>' -> sample.a > bound;
                    case '<' -> sample.a < bound;
                    // case '=' -> sample.a == bound;
                    default -> throw new IllegalStateException("Unexpected value: " + relation);
                };
                case 's' -> switch (relation) {
                    case '>' -> sample.s > bound;
                    case '<' -> sample.s < bound;
                    // case '=' -> sample.s == bound;
                    default -> throw new IllegalStateException("Unexpected value: " + relation);
                };
                default -> throw new IllegalStateException("Unexpected value: " + variable);
            };
        }

        private RangedSample[] matchesP2(RangedSample sample) {
            RangedSample trueCase = new RangedSample(sample.rangeStart.clone(),
                    sample.rangeEnd.clone());
            RangedSample falseCase = new RangedSample(sample.rangeStart.clone(),
                    sample.rangeEnd.clone());
            int index = switch (variable) {
                case 'x' -> 0;
                case 'm' -> 1;
                case 'a' -> 2;
                case 's' -> 3;
                default -> throw new IllegalStateException("Unexpected value: " + variable);
            };
            if (relation == '>') {
                trueCase.rangeStart[index] = Math.max(trueCase.rangeStart[index], bound + 1);
                falseCase.rangeEnd[index] = Math.min(falseCase.rangeEnd[index], bound);
            } else if (relation == '<') {
                trueCase.rangeEnd[index] = Math.min(trueCase.rangeEnd[index], bound - 1);
                falseCase.rangeStart[index] = Math.max(falseCase.rangeStart[index], bound);
            } else {
                throw new IllegalStateException("Unexpected value: " + relation);
            }

            return new RangedSample[]{trueCase, falseCase};
        }
    }

    private record Sample(int x, int m, int a, int s) {
    }

    private record RangedSample(int[] rangeStart, int[] rangeEnd) {
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            HashMap<String, Instruction> map = new HashMap<>();
            map.put("A", new Instruction("A", map));
            map.put("R", new Instruction("R", map));

            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.isEmpty()) {
                    break;
                }

                String name = line.substring(0, line.indexOf('{'));
                if (map.containsKey(name)) {
                    map.get(name).modify(line.substring(line.indexOf('{') + 1, line.length() - 1)
                            , map);
                } else {
                    map.put(name, new Instruction(name, line.substring(line.indexOf('{') + 1,
                            line.length() - 1), map));
                }
            }
            List<Sample> samples = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] data = line.substring(1, line.length() - 1).split(",");
                samples.add(new Sample(Integer.parseInt(data[0].split("=")[1].trim()),
                        Integer.parseInt(data[1].split("=")[1].trim()),
                        Integer.parseInt(data[2].split("=")[1].trim()),
                        Integer.parseInt(data[3].split("=")[1].trim())));
            }

            long sum = 0;
            for (Sample sample : samples) {
                boolean accepted = map.get("in").test(sample);
                if (accepted) {
                    sum += sample.x + sample.m + sample.a + sample.s;
                }
            }

            System.out.println(sum);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            HashMap<String, Instruction> map = new HashMap<>();
            map.put("A", new Instruction("A", map));
            map.put("R", new Instruction("R", map));

            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.isEmpty()) {
                    break;
                }

                String name = line.substring(0, line.indexOf('{'));
                if (map.containsKey(name)) {
                    map.get(name).modify(line.substring(line.indexOf('{') + 1, line.length() - 1)
                            , map);
                } else {
                    map.put(name, new Instruction(name, line.substring(line.indexOf('{') + 1,
                            line.length() - 1), map));
                }
            }

            // BigInteger unnecessary for part 2, didn't want to change code
            BigInteger sum = totalCombinations(map.get("in"), new RangedSample(new int[]{1, 1, 1, 1},
                    new int[]{4000, 4000
                            , 4000, 4000}));
            System.out.println(sum);
        }
    }

    private static BigInteger totalCombinations(Instruction instruction, RangedSample sample) {
        if (IntStream.range(0, 4).map(i -> sample.rangeEnd[i] - sample.rangeStart[i] + 1).anyMatch(x -> x < 0)) {
            return BigInteger.ZERO;
        }
        if (instruction.condition == null) {
            if (instruction.name.equals("A")) {
                BigInteger combinations = BigInteger.ONE;
                for (int i = 0; i < 4; i++) {
                    combinations =
                            combinations.multiply(BigInteger.valueOf(sample.rangeEnd[i] - sample.rangeStart[i] + 1));
                }
                return combinations;
            } else {
                return BigInteger.ZERO;
            }
        }
        RangedSample[] trueFalse = instruction.condition.matchesP2(sample);
        return totalCombinations(instruction.left, trueFalse[0]).add(
                totalCombinations(instruction.right, trueFalse[1]));
    }
}