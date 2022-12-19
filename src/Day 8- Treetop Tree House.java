import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

class TreetopTreeHouse {
    private static final int DAY = 8;

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println(visibleTrees("day" + DAY + "_input.txt"));
        System.out.println(treeDistance("day" + DAY + "_input.txt"));
    }

    private static int visibleTrees(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        ArrayList<int[]> forest = new ArrayList<>();
        while (s.hasNextLine()) {
            String line = s.nextLine();
            forest.add(new int[line.length()]);
            for (int i = 0; i < line.length(); i++) {
                forest.get(forest.size() - 1)[i] = line.charAt(i) - '0';
            }
        }
        int total = 0;
        for (int i = 0; i < forest.size(); i++) {
            for (int j = 0; j < forest.get(i).length; j++) {
                if (visible(i, j, forest)) {
                    total++;
                }
            }
        }
        return total;
    }

    private static int treeDistance(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        ArrayList<int[]> forest = new ArrayList<>();
        while (s.hasNextLine()) {
            String line = s.nextLine();
            forest.add(new int[line.length()]);
            for (int i = 0; i < line.length(); i++) {
                forest.get(forest.size() - 1)[i] = line.charAt(i) - '0';
            }
        }
        int max = 0;
        for (int i = 0; i < forest.size(); i++) {
            for (int j = 0; j < forest.get(i).length; j++) {
                int seen = seenTrees(i, j, forest);
                if (seen > max) {
                    //System.out.println(seen);
                    max = seen;
                }
            }
        }
        return max;
    }

    private static boolean visible(int i, int j, ArrayList<int[]> forest) {
        boolean down = true, up = true, right = true, left = true;
        for (int row = i + 1; row < forest.size(); row++) {
            if (forest.get(i)[j] <= forest.get(row)[j]) {
                down = false;
                break;
            }
        }
        for (int row = i - 1; row >= 0; row--) {
            if (forest.get(i)[j] <= forest.get(row)[j]) {
                up = false;
                break;
            }
        }
        for (int col = j + 1; col < forest.get(0).length; col++) {
            if (forest.get(i)[j] <= forest.get(i)[col]) {
                right = false;
                break;
            }
        }
        for (int col = j - 1; col >= 0; col--) {
            if (forest.get(i)[j] <= forest.get(i)[col]) {
                left = false;
                break;
            }
        }
        return down | up | left | right;
    }

    private static int seenTrees(int i, int j, ArrayList<int[]> forest) {
        int down = 0, up = 0, right = 0, left = 0;
        for (int row = i + 1; row < forest.size(); row++) {
            down++;
            if (forest.get(i)[j] <= forest.get(row)[j]) {
                break;
            }
        }
        for (int row = i - 1; row >= 0; row--) {
            up++;
            if (forest.get(i)[j] <= forest.get(row)[j]) {
                break;
            }
        }
        for (int col = j + 1; col < forest.get(0).length; col++) {
            right++;
            if (forest.get(i)[j] <= forest.get(i)[col]) {
                break;
            }
        }
        for (int col = j - 1; col >= 0; col--) {
            left++;
            if (forest.get(i)[j] <= forest.get(i)[col]) {
                break;
            }
        }
//        System.out.println(
//                "Row: " + i + " Col: " + j + " " + down + " " + up + " " + left + " " + right);
        return down * up * left * right;
    }
}