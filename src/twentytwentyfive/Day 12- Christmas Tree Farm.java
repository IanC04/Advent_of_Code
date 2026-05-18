/**
 * @author: Ian
 * @date: 12/18/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class ChristmasTreeFarm {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 12.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 12.txt"));
        partTwo(input);
    }

    private record Present(List<boolean[][]> positions) {
        private Present() {
            this(new ArrayList<>());
        }

        private Present(boolean[][] present) {
            this();
            addUniquePositions(present);
        }

        private void addUniquePositions(boolean[][] shape) {
            Set<String> seen = new HashSet<>();

            for (int i = 0; i < 4; i++) {
                addIfUnique(shape, seen, positions);
                addIfUnique(flip(shape), seen, positions);
                shape = rotate(shape);
            }
        }

        private static void addIfUnique(boolean[][] shape, Set<String> seen, List<boolean[][]> list) {
            String signature = Arrays.deepToString(shape);
            if (!seen.contains(signature)) {
                seen.add(signature);
                list.add(shape);
            }
        }

        private static boolean[][] rotate(boolean[][] mat) {
            int n = mat.length;
            int m = mat[0].length;
            boolean[][] res = new boolean[m][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    res[j][n - 1 - i] = mat[i][j];
                }
            }
            return res;
        }

        private static boolean[][] flip(boolean[][] mat) {
            int n = mat.length;
            int m = mat[0].length;
            boolean[][] res = new boolean[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    res[i][m - 1 - j] = mat[i][j];
                }
            }
            return res;
        }
    }

    private static void partOne(Scanner file) {
        final List<Present> presents = new ArrayList<>();
        int validRegions = 0;

        while (file.hasNextLine()) {
            String line = file.nextLine();

            if (line.matches("\\d:")) {
                char[][] chars = new char[3][];
                for (int i = 0; i < chars.length; i++) {
                    chars[i] = file.nextLine().toCharArray();
                }
                boolean[][] present = new boolean[3][3];
                for (int i = 0; i < chars.length; i++) {
                    for (int j = 0; j < chars[i].length; j++) {
                        present[i][j] = chars[i][j] == '#';
                    }
                }

                presents.add(new Present(present));
                file.nextLine();
            } else {
                int[] region =
                        Arrays.stream(line.split(":")[0].split("x"))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                int[] requirements =
                        Arrays.stream(line.split(": ")[1].split(" "))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                // if (isValidRegion(presents, new boolean[region[0]][region[1]], requirements, 0)) {
                //     validRegions++;
                // }
                int regionArea = region[0] * region[1];
                int maxPresentArea = Arrays.stream(requirements).sum() * (3 * 3);
                if (maxPresentArea <= regionArea) {
                    validRegions++;
                }
            }
        }

        System.out.println(validRegions);
    }

    private static boolean isValidRegion(List<Present> presents,
                                         boolean[][] region,
                                         int[] requirements,
                                         int index) {
        if (Arrays.stream(requirements).allMatch(x -> x == 0)) {
            return true;
        }
        if (index >= region.length * region[0].length) {
            return false;
        }
        int r = index / region[0].length, c = index % region[0].length;
        boolean regionValid = isValidRegion(presents, region, requirements, index + 1);
        if (regionValid) {
            return true;
        }

        if (region[r][c]) {
            return isValidRegion(presents, region, requirements, index + 1);
        }

        for (int i = 0; i < requirements.length; i++) {
            if (requirements[i] > 0) {
                for (int j = 0; j < presents.get(i).positions.size(); j++) {
                    if (placePresent(region, presents.get(i), j, r, c)) {
                        requirements[i]--;
                        regionValid = isValidRegion(presents, region, requirements, index + 1);
                        requirements[i]++;
                        unPlacePresent(region, presents.get(i), j, r, c);
                    }
                    if (regionValid) {
                        return true;
                    }
                }
            }
        }

        return regionValid;
    }


    private static boolean placePresent(boolean[][] region,
                                        Present present,
                                        int presentIndex,
                                        int r,
                                        int c) {
        boolean[][] pos = present.positions.get(presentIndex);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pos[i][j]) {
                    if (i + r >= region.length || j + c >= region[0].length) {
                        return false;
                    }
                    if (region[i + r][j + c]) {
                        return false;
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                region[i + r][j + c] |= pos[i][j];
            }
        }

        return true;
    }

    private static void unPlacePresent(boolean[][] region,
                                       Present present,
                                       int presentIndex,
                                       int r,
                                       int c) {
        boolean[][] pos = present.positions.get(presentIndex);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pos[i][j]) {
                    region[i + r][j + c] = false;
                }
            }
        }
    }

    private static void partTwo(Scanner file) {
        while (file.hasNextLine()) {
            String line = file.nextLine();
        }
    }
}