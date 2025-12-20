package Day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    private static List<String> getInput() throws IOException {
        Path path = Paths.get("input.txt");
        List<String> lines = Files.readAllLines(path);
        return List.of(lines.toArray(new String[0]));
    }

    private static final Pattern PATTERN = Pattern.compile("\\\\\\\\|\\\\\"");
    private static final Pattern HEX_PATTERN = Pattern.compile("\\\\x([0-9a-fA-F]{2})");

    private static int countMatches(String line) {
        Matcher matcher = PATTERN.matcher(line);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static int countHexaMatches(String line) {
        Matcher matcher = HEX_PATTERN.matcher(line);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static int part1(List<String> lines) {
        int res = 0;

        for (String line : lines) {
            int count = 0;
            if(line.length() > 2) {
                count += line.length()-2;
                count -= countMatches(line);
                int countHexa = countHexaMatches(line);
                count -= (countHexa * 4) - countHexa;
            }

            res += line.length() - count;
        }

        return res;
    }

    private static final Pattern PATTERN2 = Pattern.compile("[\\\\\"]");

    private static int countMatches2(String line) {
        Matcher matcher = PATTERN2.matcher(line);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static int part2(List<String> lines) {
        int res = 0;

        for (String line : lines) {
            int count = line.length() + 2;
            count += countMatches2(line);

            res += count - line.length();
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
