package twentytwentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

class CalorieCounting {
    public static void main(String[] args) throws FileNotFoundException {
        PriorityQueue<Integer> calories = getCaloriesCounts("2022 Input/day1_input.txt");
        // part 1
        int result1 = calories.peek();
        System.out.println(result1);
        // part 2
        int result2 = 0;
        for (int i = 0; i < 3; i++) {
            result2 += calories.poll();
        }
        System.out.println(result2);
    }

    public static PriorityQueue<Integer> getCaloriesCounts(
            String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        while (s.hasNextLine()) {
            int currentCalories = 0;
            for (String line = s.nextLine(); s.hasNext() && !line.isEmpty(); line = s.nextLine()) {
                currentCalories += Integer.parseInt(line);
            }
            pq.add(currentCalories);
        }
        return pq;
    }
}