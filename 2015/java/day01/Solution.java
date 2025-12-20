package Day01;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Solution {
    private static String getInput() throws IOException {
        Path path = Paths.get("input.txt");
        return Files.readString(path);
    }

    private static void part1(String input) {
        int res = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                res++;
            } else {
                res--;
            }
        }
        System.out.println("Part1 : " + res);
    }

    private static void part2(String input) {
        int res = 0, i = 0;
        while (i < input.length() && res >= 0) {
            char c = input.charAt(i);
            if (c == '(') {
                res++;
            }  else {
                res--;
            }
            i++;
        }
        System.out.println("Part2 : " + i);
    }

    public static void main(String[] args) {
        try {
            String input = getInput();
            System.out.println("Input : \n" + input);
            part1(input);
            part2(input);
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
