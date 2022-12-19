import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class NoSpaceLeftOnDevice {
    private static final int DAY = 7;

    public static void main(String[] args) throws FileNotFoundException {
        //lessThan100_000Sum("day" + DAY + "_input.txt");
        deleteLargestBoundedFile("day" + DAY + "_input.txt");
    }

    private static void deleteLargestBoundedFile(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        Directory root = new Directory(true, null, "/", 0);
        createTree(s, root);
        System.out.println(findBoundedFile(root, root.size, 40_000_000));
    }

    private static int findBoundedFile(Directory d, int spaceUsed, int usableSpace) {
        int min = Integer.MAX_VALUE;
        if (d.children != null) {
            for (Directory child : d.children) {
                int cur = findBoundedFile(child, spaceUsed, usableSpace);
                if (cur < min) {
                    min = cur;
                }
            }
        }
        if (min == Integer.MAX_VALUE && spaceUsed - d.size <= usableSpace) {
            return d.size;
        }
        return min;
    }

    private static void lessThan100_000Sum(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        Directory root = new Directory(true, null, "/", 0);
        createTree(s, root);
        System.out.println(sumWithCondition(root, 100_000));
    }

    private static int sumWithCondition(Directory d, int bound) {
        int sum = 0;
        if (d != null) {
            if (d.children != null) {
                if (d.size <= bound) {
                    sum = d.size;
                }
                for (Directory child : d.children) {
                    sum += sumWithCondition(child, bound);
                }
            }
        }
        return sum;
    }

    private static void createTree(Scanner s, Directory root) {
        Directory current = root;
        while (s.hasNext()) {
            String line = s.nextLine();
            String[] tokens = line.split(" ");
            if (line.charAt(0) == '$') {
                switch (tokens[1]) {
                    case "cd":
                        current = switchDirectory(current, tokens[2]);
                    case "ls":
                }
                continue;
            }
            // add this line to the current directory
            if (tokens[0].equals("dir")) {
                current.children.add(new Directory(true, current, tokens[1], 0));
            } else {
                int fileSize = Integer.parseInt(tokens[0]);
                current.children.add(new Directory(false, current, tokens[1], fileSize));
                addFileSize(current, fileSize);
            }
        }
    }

    private static void addFileSize(Directory current, int fileSize) {
        current.size += fileSize;
        if (current.parent != null) {
            addFileSize(current.parent, fileSize);
        }
    }

    private static Directory switchDirectory(Directory d, String token) {
        switch (token) {
            case "..":
                return d.parent;
            case "/":
                return d.parent == null ? d : switchDirectory(d.parent, token);
        }
        for (Directory child : d.children) {
            if (token.equals(child.name)) {
                return child;
            }
        }
        throw new IllegalStateException("Should have found a directory to go to.");
    }

    static final class Directory {
        int size;
        String name;

        Directory parent;
        ArrayList<Directory> children;

        private Directory(boolean isFolder, Directory parent, String name, int size) {
            this.parent = parent;
            this.name = name;
            if (!isFolder) {
                this.size = size;
            } else {
                children = new ArrayList<>();
            }
        }

        public String toString() {
            return "Name: " + name + " | Size: " + size;
        }
    }
}