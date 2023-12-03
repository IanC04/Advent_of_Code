package twentytwentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

class SupplyStacks {

    private static ArrayList<Stack<Character>> crateStacks;

    public static void main(String[] args) throws FileNotFoundException {
        //topCrates("2022/day5_input.txt", false);
        topCrates("2022/day5_input.txt", true);
        for (Stack<Character> crateStack : crateStacks) {
            System.out.print(crateStack.peek());
        }
    }

    private static void topCrates(String fileName, boolean keptOrder) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        crateStacks = new ArrayList<>();
        for (String line = s.nextLine(); !line.isEmpty(); line = s.nextLine()) {
            for (int i = 0; i < line.length(); i += 4) {
                if (line.contains("1")) {
                    continue;
                }
                int index = i / 4;
                if (crateStacks.size() <= index) {
                    crateStacks.add(new Stack<>());
                }
                if (line.charAt(i + 1) != ' ') {
                    crateStacks.get(index).push(line.charAt(i + 1));
                }
            }
        }
        for (Stack<Character> crateStack : crateStacks) {
            Collections.reverse(crateStack);
        }
        // moving the boxes after initial setup
        s.useDelimiter("\\D+");
        while (s.hasNextInt()) {
            int numMoved = s.nextInt(), initialStack = s.nextInt() - 1, toStack = s.nextInt() - 1;
            if (keptOrder) {
                Stack<Character> tempStack = new Stack<>();
                while (numMoved-- > 0) {
                    tempStack.push(crateStacks.get(initialStack).pop());
                }
                while (!tempStack.isEmpty()) {
                    crateStacks.get(toStack).push(tempStack.pop());
                }
            } else {
                while (numMoved-- > 0) {
                    crateStacks.get(toStack).push(crateStacks.get(initialStack).pop());
                }
            }
        }
    }
}