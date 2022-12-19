import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.io.FileNotFoundException;

class RopeBridge {
    private static final int DAY = 9;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(visitedCells("day" + DAY + "_input.txt", 10));
    }

    private static int visitedCells(String fileName, int numRopes) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        Position[] rope = new Position[numRopes];
        for (int i = 0; i < numRopes; i++) {
            rope[i] = new Position();
        }
        while (s.hasNext()) {
            String direction = s.next();
            int steps = Integer.parseInt(s.next());
            while (steps-- > 0) {
                rope[0].upDatePosition(1, direction);
                for (int i = 1; i < numRopes; i++) {
                    rope[i].magnetTo(rope[i - 1]);
                }
            }
        }
        //System.out.println(tail.allPositions());
        return rope[numRopes - 1].getNumSquaresTouched();
    }

    private static class Position {


        private enum DIRECTION {
            U, D, L, R;

        }

        private int currentRow, currentCol;
        private HashMap<Integer, HashSet<Integer>> squaresReached;

        private Position() {
            currentRow = currentCol = 0;
            squaresReached = new HashMap<>();
            squaresReached.put(0, new HashSet<>());
            squaresReached.get(0).add(0);
        }

        private void upDatePosition(int steps, String direction) {
            for (int i = 0; i < direction.length(); i++) {
                switch (DIRECTION.valueOf(direction.charAt(i) + "")) {
                    case U -> currentRow -= steps;
                    case D -> currentRow += steps;
                    case L -> currentCol -= steps;
                    case R -> currentCol += steps;
                }
            }
            if (!squaresReached.containsKey(currentRow)) {
                squaresReached.put(currentRow, new HashSet<>());
            }
            squaresReached.get(currentRow).add(currentCol);
        }

        private int getNumSquaresTouched() {
            int total = 0;
            for (int row : squaresReached.keySet()) {
                total += squaresReached.get(row).size();
            }
            return total;
        }

        private void magnetTo(Position head) {
//            if (Math.abs(currentRow - head.currentRow) > 1 && Math.abs(
//                    currentCol - head.currentCol) > 1) {
//                throw new IllegalStateException("Shouldn't be this far away.");
//            }
            int rowDiff = currentRow - head.currentRow, colDiff = currentCol - head.currentCol;
            String moves = "";
            if (Math.abs(rowDiff) > 1 || Math.abs(colDiff) > 1) {
                if (rowDiff <= -1) {
                    moves += 'D';
                }
                if (rowDiff >= 1) {
                    moves += 'U';
                }
                if (colDiff <= -1) {
                    moves += 'R';
                }
                if (colDiff >= 1) {
                    moves += 'L';
                }
                this.upDatePosition(1, moves);
            }
        }

        public String allPositions() {
            StringBuilder output = new StringBuilder();
            for (int row : squaresReached.keySet()) {
                for (int col : squaresReached.get(row)) {
                    output.append(row).append(", ").append(col).append("\n");
                }
            }
            return output.toString();
        }
    }
}