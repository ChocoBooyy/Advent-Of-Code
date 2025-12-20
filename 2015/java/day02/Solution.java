package Day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

public class Solution {
    private static List<String> getInput() throws IOException {
        Path path = Paths.get("input.txt");
        List<String> lines = Files.readAllLines(path);
        return List.of(lines.toArray(new String[0]));
    }

    private static int part1(List<String> input) {
        int res = 0;
        for (String line : input) {
            Pattern p = Pattern.compile("(\\d+)x(\\d+)x(\\d+)");
            Matcher m = p.matcher(line);

            if (m.find()) {
                int l = Integer.parseInt(m.group(1));
                int w = Integer.parseInt(m.group(2));
                int h = Integer.parseInt(m.group(3));

                int lineRes = 2*(l*w + w*h + h*l) + Math.min(l*w, Math.min(w*h, h*l));
                res += lineRes;
                System.out.printf("Present %s -> %dx%dx%d -> %d%n", line, l, w, h, lineRes);
            } else {
                System.err.println("No match found");
            }
        }
        System.out.printf("Total wrapping paper (Part 1): %d%n%n", res);
        return res;
    }

    private static int part2(List<String> input) {
        int res = 0;
        for (String line : input) {
            Pattern p = Pattern.compile("(\\d+)x(\\d+)x(\\d+)");
            Matcher m = p.matcher(line);

            if (m.find()) {
                int l = Integer.parseInt(m.group(1));
                int w = Integer.parseInt(m.group(2));
                int h = Integer.parseInt(m.group(3));

                int[] dims = {l, w, h};
                Arrays.sort(dims);
                int lineRes = 2 * (dims[0] + dims[1]) + (l * w * h);
                res += lineRes;
                System.out.printf("Present %s -> %dx%dx%d -> %d%n", line, l, w, h, lineRes);
            } else {
                System.err.println("No match found");
            }
        }
        System.out.printf("Total ribbon (Part 2): %d%n%n", res);
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
