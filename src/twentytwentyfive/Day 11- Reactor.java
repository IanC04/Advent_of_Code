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
class Reactor {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("2025 Input/day 11.txt"));
        partOne(input);
        input = new Scanner(new File("2025 Input/day 11.txt"));
        partTwo(input);
    }

    private static void partOne(Scanner file) {
        Map<String, List<String>> graph = new HashMap<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            String key = line.split(":")[0];
            List<String> value = Arrays.asList(line.split(":")[1].trim().split(" "));
            graph.put(key, value);
        }

        // No cycles in the input (found by trial)
        System.out.println(dfs("you", graph));
    }

    private static long dfs(String cur, Map<String, List<String>> graph) {
        if (cur.equals("out")) {
            return 1;
        }
        long total = 0;
        for (String next : graph.get(cur)) {
            total += dfs(next, graph);
        }

        return total;
    }

    private static void partTwo(Scanner file) {
        Map<String, List<String>> graph = new HashMap<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            String key = line.split(":")[0];
            List<String> value = Arrays.asList(line.split(":")[1].trim().split(" "));
            graph.put(key, value);
        }

        // No cycles in the input (found by trial)
        Map<String, Long> memo = new HashMap<>();
        memo.put("out" + false + false, 1L);
        memo.put("out" + false + true, 0L);
        memo.put("out" + true + false, 0L);
        memo.put("out" + true + true, 0L);
        System.out.println(dfsTwo("svr", graph, false, false, memo));
    }

    private static long dfsTwo(String cur,
                               Map<String, List<String>> graph,
                               boolean visitFft,
                               boolean visitDac,
                               Map<String, Long> memo) {
        if (cur.equals("fft")) {
            visitFft = true;
        }
        if (cur.equals("dac")) {
            visitDac = true;
        }
        if (memo.containsKey(cur + !visitFft + !visitDac)) {
            return memo.get(cur + !visitFft + !visitDac);
        }

        long total = 0;
        for (String next : graph.get(cur)) {
            total += dfsTwo(next, graph, visitFft, visitDac, memo);
        }

        memo.put(cur + !visitFft + !visitDac, total);
        return total;
    }
}