package day01;

import common.InputFiles;
import java.io.IOException;
import java.util.List;

public class Solution {
    private static String getInput(boolean example) throws IOException {
        return InputFiles.readString(Solution.class, example);
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
            String input = getInput(true);
            System.out.println("Input : \n" + input);
            part1(input);
            part2(input);
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
