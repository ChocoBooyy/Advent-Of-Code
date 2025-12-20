package Day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.*;

public class Solution {
    private static List<String> getInput() throws IOException {
        Path path = Paths.get("input.txt");
        List<String> lines = Files.readAllLines(path);
        return List.of(lines.toArray(new String[0]));
    }

    private static int part1(List<String> lines) {
        boolean[][] grid = new boolean[1000][1000];

        for (String line : lines) {
            Pattern pattern = Pattern.compile("(\\d+),(\\d+)");
            Matcher matcher = pattern.matcher(line);

            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

            if (matcher.find()) {
                x1 = Integer.parseInt(matcher.group(1));
                y1 = Integer.parseInt(matcher.group(2));
            }
            if (matcher.find()) {
                x2 = Integer.parseInt(matcher.group(1));
                y2 = Integer.parseInt(matcher.group(2));
            }

            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    if (line.startsWith("turn on")) {
                        grid[x][y] = true;
                    } else if (line.startsWith("turn off")) {
                        grid[x][y] = false;
                    } else {
                        grid[x][y] = !grid[x][y];
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j]) res++;
            }
        }

        return res;
    }

    private static int part2(List<String> lines) {
        int[][] grid = new int[1000][1000];

        for (String line : lines) {
            Pattern pattern = Pattern.compile("(\\d+),(\\d+)");
            Matcher matcher = pattern.matcher(line);

            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

            if (matcher.find()) {
                x1 = Integer.parseInt(matcher.group(1));
                y1 = Integer.parseInt(matcher.group(2));
            }
            if (matcher.find()) {
                x2 = Integer.parseInt(matcher.group(1));
                y2 = Integer.parseInt(matcher.group(2));
            }

            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    if (line.startsWith("turn on")) {
                        grid[x][y] += 1;
                    } else if (line.startsWith("turn off")) {
                        grid[x][y] = Math.max(0, grid[x][y] - 1);
                    } else {
                        grid[x][y] += 2;
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                res += grid[i][j];
            }
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
