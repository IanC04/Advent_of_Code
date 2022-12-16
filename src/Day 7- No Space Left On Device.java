import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

class NoSpaceLeftOnDevice {
    private static final int DAY = 7;

    public static void main(String[] args) throws FileNotFoundException {
        lessThan100_000Sum("day" + DAY + "_input.txt");
    }

    private static void lessThan100_000Sum(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner s = new Scanner(f);
        Directory root = new Directory(true, null, "/", 0);
        createTree(s, root);
        System.out.println(sumWithCondition(root, 100_000));
    }

    private static int sumWithCondition(Directory d, int bound) {
        if (d != null) {
            if (d.children != null) {
                int sum = 0;
                for (Directory child : d.children) {
                    sum += sumWithCondition(child, bound);
                }
                return sum;
            }
            if (d.size <= bound) {
                System.out.println(d);
                return d.size;
            }
        }
        return 0;
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
                current.children.add(
                        new Directory(false, current, tokens[1], Integer.parseInt(tokens[0])));
            }
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