/**
 * @author: Ian
 * @date: 12/17/2025
 */

package twentytwentyfive;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * @see <a href=https://github.com/IanC04>My GitHub</a>
 */
class Playground {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 8.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 8.txt"));
        partTwo(input);
    }

    private record Edge(long distance, String v1, String v2) {
    }

    private static void partOne(Scanner file) {
        List<String> nodes = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            nodes.add(line);
        }

        Set<Set<String>> components = buildComponents(nodes);
        PriorityQueue<Edge> edges = buildEdges(nodes);

        for (int i = 0; i < 1_000; i++) {
            Edge e = edges.poll();

            Set<String> setV1 = null, setV2 = null;
            for (Set<String> component : components) {
                if (component.contains(e.v1)) {
                    setV1 = component;
                }
                if (component.contains(e.v2)) {
                    setV2 = component;
                }
            }

            components.remove(setV1);
            components.remove(setV2);
            setV1.addAll(setV2);
            components.add(setV1);
        }

        long size = components
                .stream()
                .map(Set::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (a, b) -> a * b);
        System.out.println(size);
    }

    private static Set<Set<String>> buildComponents(List<String> nodes) {
        Set<Set<String>> components = new HashSet<>();
        for (String c1 : nodes) {
            components.add(new HashSet<>(Set.of(c1)));
        }

        return components;
    }

    private static PriorityQueue<Edge> buildEdges(List<String> nodes) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingLong(e -> e.distance));

        for (int i = 0; i < nodes.size(); i++) {
            String c1 = nodes.get(i);
            long[] pos = Arrays.stream(c1.split(",")).mapToLong(Long::parseLong).toArray();

            for (int j = i + 1; j < nodes.size(); j++) {
                String c2 = nodes.get(j);
                long[] cur = Arrays.stream(c2.split(",")).mapToLong(Long::parseLong).toArray();

                long distance = 0;
                for (int k = 0; k < cur.length; k++) {
                    distance += (pos[k] - cur[k]) * (pos[k] - cur[k]);
                }
                pq.add(new Edge(distance, c1, c2));
            }
        }

        return pq;
    }

    private static void partTwo(Scanner file) {
        List<String> nodes = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            nodes.add(line);
        }

        Set<Set<String>> components = buildComponents(nodes);
        PriorityQueue<Edge> edges = buildEdges(nodes);

        Edge lastEdge = null;
        while (components.size() > 1) {
            Edge e = edges.poll();

            Set<String> setV1 = null, setV2 = null;
            for (Set<String> component : components) {
                if (component.contains(e.v1)) {
                    setV1 = component;
                }
                if (component.contains(e.v2)) {
                    setV2 = component;
                }
            }

            components.remove(setV1);
            components.remove(setV2);
            setV1.addAll(setV2);
            components.add(setV1);

            lastEdge = e;
        }

        long x1 = Long.parseLong(lastEdge.v1.substring(0, lastEdge.v1.indexOf(',')));
        long x2 = Long.parseLong(lastEdge.v2.substring(0, lastEdge.v2.indexOf(',')));
        System.out.println(x1 * x2);
    }
}