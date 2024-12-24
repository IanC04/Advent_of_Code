/**
 * @author: Ian Chen
 * @date: 12/28/2024
 */

package twentytwentyfour;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class DiskFragmenter {
    private static final int DAY = 9;

    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            String line = s.nextLine();
            StringBuilder filesStr = new StringBuilder();
            StringBuilder spacesStr = new StringBuilder();
            for (int i = 0; i < line.length(); i += 2) {
                filesStr.append(line.charAt(i));
                if (i < line.length() - 1) {
                    spacesStr.append(line.charAt(i + 1));
                }
            }
            int[] files = new int[filesStr.length()];
            for (int i = 0; i < filesStr.length(); i++) {
                files[i] = Integer.parseInt(filesStr.substring(i, i + 1));
            }
            int[] spaces = new int[spacesStr.length()];
            for (int i = 0; i < spacesStr.length(); i++) {
                spaces[i] = Integer.parseInt(spacesStr.substring(i, i + 1));
            }

            int index = files[0];
            long checksum = 0;
            int spaceIndex = 0, fileIndex = 1;
            for (int fileId = files.length - 1; fileId >= fileIndex; fileId--) {
                while (files[fileId] > 0) {
                    if (spaces[spaceIndex] == 0) {
                        spaceIndex++;
                        while (files[fileIndex] > 0) {
                            checksum += (long) index++ * fileIndex;
                            files[fileIndex]--;
                        }
                        fileIndex++;
                        continue;
                    }
                    checksum += (long) index++ * fileId;
                    spaces[spaceIndex]--;
                    files[fileId]--;
                }
            }
            System.out.println(checksum);
        }
    }

    private static class SystemFile {
        private int id, length;

        private SystemFile(int id, int length) {
            this.id = id;
            this.length = length;
        }

        public String toString() {
            return "(" + id + ", " + length + ")";
        }
    }

    private static void part2() throws FileNotFoundException {
        try (Scanner s = new Scanner(new File("2024 Input/day" + DAY + "_input.txt"))) {
            String line = s.nextLine();
            List<SystemFile> filesystem = new ArrayList<>();
            boolean space = false;
            for (int i = 0; i < line.length(); i++) {
                int length = Integer.parseInt(line.substring(i, i + 1));
                filesystem.add(new SystemFile(space ? -1 : i / 2, length));
                space = !space;
            }
            for (int fileIndex = filesystem.size() - 1; fileIndex >= 0; fileIndex--) {
                if (filesystem.get(fileIndex).id == -1) {
                    continue;
                }
                SystemFile fileToInsert = filesystem.get(fileIndex);
                int freeSpaceIndex = 0;
                while (!(filesystem.get(freeSpaceIndex).id == -1 && filesystem.get(freeSpaceIndex).length >= fileToInsert.length)) {
                    freeSpaceIndex++;
                    if (freeSpaceIndex >= fileIndex) {
                        break;
                    }
                }
                if (freeSpaceIndex >= fileIndex) {
                    continue;
                }
                filesystem.set(fileIndex, new SystemFile(-1, fileToInsert.length));
                filesystem.add(freeSpaceIndex, fileToInsert);
                filesystem.get(freeSpaceIndex + 1).length -= fileToInsert.length;
                fileIndex++;
            }
            long checksum = 0;
            int index = 0;
            for (SystemFile file : filesystem) {
                if (file.id == -1) {
                    index += file.length;
                    continue;
                }
                while (file.length-- > 0) {
                    checksum += (long) index++ * file.id;
                }
            }
            System.out.println(checksum);
        }
    }
}