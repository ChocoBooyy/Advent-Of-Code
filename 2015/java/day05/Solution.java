package Day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Solution {
    private static List<String> getInput() throws IOException {
        Path path = Paths.get("input.txt");
        List<String> lines = Files.readAllLines(path);
        return List.of(lines.toArray(new String[0]));
    }

    private static int part1(List<String> lines) {
        int res = 0;

        for (String line : lines) {
            if (line.matches("^(?=(?:.*[aeiou]){3,})(?=.*(.)\\1)(?!.*(?:ab|cd|pq|xy)).*$"))
                res++;
        }

        return res;
    }

    private static int part2(List<String> lines) {
        int res = 0;

        for (String line : lines) {
            if (line.matches("^(?=.*(..).*\\1)(?=.*(.).\\2).*$"))
                res++;
        }

        return res;
    }

    public static void main(String[] args) {
        try {
            List<String> input = getInput();
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
