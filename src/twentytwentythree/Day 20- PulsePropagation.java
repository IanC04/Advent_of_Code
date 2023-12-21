/**
 * @author: Ian Chen
 * GitHub: https://github.com/IanC04
 */

package twentytwentythree;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

class PulsePropagation {
    private static final int DAY = 20;

    public static void main(String[] args) throws FileNotFoundException {
        // part1();
        part2();
    }

    private static class Instruction {
        private final String name;

        private enum Type {
            FLIP_FLOP, CONJUNCTION, BROADCAST
        }

        private Type type;

        private final List<Instruction> destinations;

        private boolean state;

        // For conjunction components
        private HashMap<Instruction, Boolean> inputStates;

        private Instruction(String name) {
            this.name = name;
            this.destinations = new ArrayList<>();
            this.state = false;
            this.inputStates = new HashMap<>();
        }

        private void modify(String data, HashMap<String, Instruction> map) {
            this.type = switch (data.charAt(0)) {
                case '%' -> Type.FLIP_FLOP;
                case '&' -> Type.CONJUNCTION;
                default -> Type.BROADCAST;
            };

            String[] destinations = data.substring(data.indexOf('>') + 2).split(", ");
            for (String destination : destinations) {
                if (map.containsKey(destination)) {
                    this.destinations.add(map.get(destination));
                    map.get(destination).inputStates.put(this, false);
                } else {
                    Instruction newInstruction = new Instruction(destination);
                    this.destinations.add(newInstruction);
                    newInstruction.inputStates.put(this, false);
                    map.put(destination, newInstruction);
                }
            }
        }

        private void operate(boolean pulse, Queue<Signal> queue, long[] pulses) {
            if (type == null || destinations.isEmpty()) {
                // Hard-coded for this problem since rx is the only instruction with no destinations
                assert name.equals("rx") : "Invalid instruction: " + this;
                return;
            }
            switch (type) {
                case FLIP_FLOP -> {
                    if (pulse) {
                        return;
                    }
                    state = !state;
                    boolean pulseToSend = state;
                    for (Instruction destination : destinations) {
                        destination.inputStates.put(this, pulseToSend);
                        ++pulses[pulseToSend ? 1 : 0];
                        queue.add(new Signal(pulseToSend, destination));
                    }
                }
                case CONJUNCTION -> {
                    boolean pulseToSend = !inputStates.values().stream().reduce(true,
                            Boolean::logicalAnd);
                    state = pulseToSend;
                    for (Instruction destination : destinations) {
                        destination.inputStates.put(this, pulseToSend);
                        ++pulses[pulseToSend ? 1 : 0];
                        queue.add(new Signal(pulseToSend, destination));
                    }
                }
                case BROADCAST -> {
                    boolean pulseToSend = pulse;
                    state = pulseToSend;
                    for (Instruction destination : destinations) {
                        destination.inputStates.put(this, pulseToSend);
                        ++pulses[pulseToSend ? 1 : 0];
                        queue.add(new Signal(pulseToSend, destination));
                    }
                }
            }
        }

        public String toString() {
            return name + " | " + state + " | " + type + " | " + destinations.stream().map(destination -> destination.name + " ").reduce("", String::concat).trim();
        }
    }

    private record Signal(boolean pulse, Instruction source) {
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            HashMap<String, Instruction> map = new HashMap<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();

                String name = line.substring(Math.max(line.indexOf('%'),
                        line.indexOf('&')) + 1, line.indexOf('-') - 1);
                if (map.containsKey(name)) {
                    map.get(name).modify(line, map);
                } else {
                    Instruction newInstruction = new Instruction(name);
                    newInstruction.modify(line, map);
                    map.put(name, newInstruction);
                }
            }

            Queue<Signal> queue = new LinkedList<>();
            long[] pulses = new long[2];
            for (int i = 0; i < 1000; i++) {
                queue.add(new Signal(false, map.get("broadcaster")));
                ++pulses[0];
                while (!queue.isEmpty()) {
                    Signal signal = queue.poll();
                    // System.out.println(signal);
                    signal.source.operate(signal.pulse, queue, pulses);
                }
            }
            System.out.println(Arrays.toString(pulses) + " " + pulses[0] * pulses[1]);
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2023 Input/day" + DAY + "_input.txt"))) {
            HashMap<String, Instruction> map = new HashMap<>();
            while (s.hasNextLine()) {
                String line = s.nextLine();

                String name = line.substring(Math.max(line.indexOf('%'),
                        line.indexOf('&')) + 1, line.indexOf('-') - 1);
                if (map.containsKey(name)) {
                    map.get(name).modify(line, map);
                } else {
                    Instruction newInstruction = new Instruction(name);
                    newInstruction.modify(line, map);
                    map.put(name, newInstruction);
                }
            }

            Queue<Signal> queue = new LinkedList<>();
            HashMap<Instruction, Long> neededHigh = new HashMap<>();
            for (Instruction instruction : map.get("ft").inputStates.keySet()) {
                neededHigh.put(instruction, 0L);
            }
            int neededToFind = neededHigh.size();

            long[] pulses = new long[2];
            for (long i = 1; i < 10_000; i++) {
                // System.out.println("Button press: " + i);
                queue.add(new Signal(false, map.get("broadcaster")));
                ++pulses[0];
                while (!queue.isEmpty()) {
                    Signal signal = queue.poll();
                    // System.out.println(signal);
                    signal.source.operate(signal.pulse, queue, pulses);
                    if (neededHigh.containsKey(signal.source) && neededHigh.get(signal.source) == 0 && signal.source.state) {
                        System.out.println(signal);
                        --neededToFind;
                        neededHigh.put(signal.source, i);
                        if (neededToFind == 0) {
                            System.out.println(LCM(neededHigh.values().stream().mapToLong(Long::longValue).toArray()));
                            return;
                        }
                    }
                }
                // Not completely sure why this doesn't work or why the above works instead. I
                // know cyclic property is reason, but not sure why it doesn't work here.
                /*for (Instruction instruction : neededHigh.keySet()) {
                    if (neededHigh.get(instruction) == 0 && instruction.state) {
                        neededHigh.put(instruction, i);
                        System.out.println(instruction.name + " " + i);
                        --neededToFind;
                        if (neededToFind == 0) {
                            System.out.println(LCM(neededHigh.values().stream().mapToLong(Long::longValue).toArray()));
                            return;
                        }
                    }
                }*/
            }

            System.out.println("Error, didn't find all high signals");
        }
    }

    private static long LCM(long[] numbers) {
        long lcm = numbers[0];
        for (int i = 1; i < numbers.length; i++)
            lcm = lcm * numbers[i] / GCD(lcm, numbers[i]);
        return lcm;
    }

    private static long GCD(long a, long b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }
}