package Day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    /*
 0 0 0 0 - 1
 1 0 0 0 - 2
 1 1 0 0 - 3
 1 1 1 0 - 4
 1 1 1 1 - 4

 0 0 - 1
 1 0 - 2
 1 1 - 3
 0 1 - 4
 0 0 - 4
*/
    private static String getInput() throws IOException {
        Path path = Paths.get("input.txt");
        return Files.readString(path);
    }

    private static int part1(String input) {
        int res = 1, ns = 0, ew = 0;

        List<int[]> list = new ArrayList<>();
        list.add(new int[]{ns, ew});

        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '^' -> ns++;
                case 'v' -> ns--;
                case '>' -> ew++;
                case '<' -> ew--;
            }
            int[] check = new int[]{ns, ew};

            boolean exists = false;
            for (int[] p : list) {
                if (p[0] == check[0] && p[1] == check[1]) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                list.add(check);
                res++;
            }
        }
        return res;
    }

    private static int part2(String input) {
        int res = 1, ns = 0, ew = 0, rns = 0, rew = 0;

        List<int[]> list = new ArrayList<>();
        list.add(new int[]{ns, ew});

        for (int i = 0; i < input.length(); i++) {
            boolean robot = i%2 != 0;

            if (robot) {
                switch (input.charAt(i)) {
                    case '^' -> rns++;
                    case 'v' -> rns--;
                    case '>' -> rew++;
                    case '<' -> rew--;
                }
            } else {
                switch (input.charAt(i)) {
                    case '^' -> ns++;
                    case 'v' -> ns--;
                    case '>' -> ew++;
                    case '<' -> ew--;
                }
            }

            int[] check;

            if (robot) {
                check = new int[]{rns, rew};
            } else {
                check = new int[]{ns, ew};
            }

            boolean exists = false;
            for (int[] p : list) {
                if (p[0] == check[0] && p[1] == check[1]) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                list.add(check);
                res++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        try {
            String input = getInput();
            System.out.println("Input : \n" + input);
            int res1 = part1(input);
            int res2 = part2(input);

            System.out.println("=== Final Results ===");
            System.out.printf("Part 1: %d%n", res1);
            System.out.printf("Part 2: %d%n", res2);
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
