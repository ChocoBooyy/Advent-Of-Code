package day1;

import common.InputFiles;
import java.io.IOException;
import java.util.List;

public class Solution {
    private static List<String> getInput(boolean example) throws IOException {
        return InputFiles.readNonEmptyLines(Solution.class, example);
    }

    private static int part1(List<String> input) {
        int res = 0, dial = 50;

        for (String line : input) {
            char direction = line.charAt(0);
            int rotation = Integer.parseInt(line.substring(1).trim());
            dial = Math.floorMod(dial + (direction == 'R' ? rotation : -rotation), 100);
            if (dial == 0) res++;
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = getInput(false);
        System.out.println("Part 1: " + part1(input));
    }
}
