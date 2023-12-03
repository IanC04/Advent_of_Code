package twentytwentytwo;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

class MonkeyInTheMiddle {
    private static final int DAY = 11;

    public static void main(String[] args) throws FileNotFoundException {
        countInspections("2022 Input/day" + DAY + "_input.txt");
    }

    private static void countInspections(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        ArrayList<Monkey> monkeys = new ArrayList<>();
        setUpMonkeys(s, monkeys);
        //System.out.println(monkeys);
        for (int i = 0; i < 10_000; i++) {
            updateItems(monkeys);
            System.out.println(monkeys);
        }
        monkeys.sort((m1, m2) -> m2.inspectionTimes - m1.inspectionTimes);
        System.out.println(monkeys);
        System.out.println(monkeys.get(0).inspectionTimes * monkeys.get(1).inspectionTimes);
    }

    private static void updateItems(ArrayList<Monkey> monkeys) {
        for (Monkey m : monkeys) {
            while (!m.items.isEmpty()) {
                long[] solution = m.updateItem();
                // all mods multiplied together to help prevent overflow
                solution[0] %= 9699690;
                monkeys.get((int)solution[1]).items.add(solution[0]);
            }
        }
    }

    private static void setUpMonkeys(Scanner s, ArrayList<Monkey> monkeys) {
        while (s.hasNextLine()) {
            Monkey cur = new Monkey(monkeys.size());
            monkeys.add(cur);
            s.nextLine();
            String[] line = s.nextLine().substring(18).split(", ");
            for (String value : line) {
                cur.addItem(Integer.parseInt(value));
            }
            line = s.nextLine().split(" +");
            cur.setOperation(line[4], line[5].charAt(0), line[6]);
            line = s.nextLine().split(" +");
            cur.setDivisor(Integer.parseInt(line[4]));
            String first = s.nextLine(), second = s.nextLine();
            cur.setMonkeys(Integer.parseInt(first.substring(first.lastIndexOf(' ') + 1)),
                    Integer.parseInt(second.substring(second.lastIndexOf(' ') + 1)));
            if (s.hasNextLine()) {
                s.nextLine();
            }
        }
    }

    private static class Monkey {
        int tag;

        ArrayList<Long> items;

        int inspectionTimes;

        int divisor;

        char operation;

        String operand1, operand2;

        int ifTrue, ifFalse;

        private Monkey(int tag) {
            items = new ArrayList<>();
            this.tag = tag;
        }

        private void addItem(long itemNum) {
            items.add(itemNum);
        }

        private void setDivisor(int divisor) {
            this.divisor = divisor;
        }

        private void setOperation(String operand1, char operation, String operand2) {
            this.operand1 = operand1;
            this.operation = operation;
            this.operand2 = operand2;
        }

        private void setMonkeys(int t, int f) {
            ifTrue = t;
            ifFalse = f;
        }

        private long[] updateItem() {
            long[] val = new long[2];
            inspectionTimes++;
            long item = items.remove(items.size() - 1);
            long o1 = item, o2 = switch (operand2) {
                case "old":
                    yield o1;
                default:
                    yield Long.parseLong(operand2);
            };
            item = switch (operation) {
                case '*' -> o1 * o2;
                case '+' -> o1 + o2;
                case '-' -> o1 - o2;
                case '/' -> o1 / o2;
                default -> throw new IllegalStateException("Shouldn't be here.");
            };
            // first part divides 3
            //val[0] = item / 3;
            val[0] = item;
            val[1] = val[0] % divisor == 0 ? ifTrue : ifFalse;
            return val;
        }

        public String toString() {
            return "Tag: " + tag + ", inspection times: " + inspectionTimes + ", items: " + items +
                    ", divisor: " + divisor + ", " + "operand1: " + operand1 + ", operation: " +
                    operation + ", " + "operand2: " + operand2 + ", monkey1: " + ifTrue +
                    ", monkey2: " + ifFalse;
        }
    }
}