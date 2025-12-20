package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Solution {
    private static String getInput() throws IOException {
        Path path = Paths.get("input.txt");
        return Files.readString(path);
    }

    private static boolean iolRule(String str) {
        return !(str.contains("i") || str.contains("o") || str.contains("l"));
    }

    private static boolean increaseRule(String str) {
        if (str == null || str.length() < 3) return false;
        int count = 1;
        for (int i = 0; i < str.length() - 1 && count < 3; i++) {
            if (str.charAt(i + 1) == (char)(str.charAt(i) + 1)) {
                count++;
            } else {
                count = 1;
            }
        }
        return count >= 3;
    }

    private static boolean pairsRule(String str) {
        int countPair = 0;
        char firstPairChar = '\0';         // meaning "none yet"
        for (int i = 0; i < str.length() - 1 && countPair < 2; i++) {
            if (str.charAt(i) == str.charAt(i + 1) && str.charAt(i) != firstPairChar) {
                countPair++;
                firstPairChar = str.charAt(i);
                i++;
            }
        }
        return countPair >= 2;
    }

    private static boolean isValid(String str){
        return iolRule(str) && increaseRule(str) && pairsRule(str);
    }

    private static String increment(String s) {
        char[] arr = s.toCharArray();
        int i = arr.length - 1;

        while (i >= 0) {
            if (arr[i] == 'z') {
                arr[i] = 'a';
                i--;
                continue;
            }

            arr[i]++;

            if (arr[i] == 'i' || arr[i] == 'o' || arr[i] == 'l') {
                arr[i]++;
                for (int j = i + 1; j < arr.length; j++) {
                    arr[j] = 'a';
                }
            }

            break;
        }

        return new String(arr);
    }

    private static String nextValidPassword(String input) {
        String candidate = increment(input);
        while (!isValid(candidate)) {
            candidate = increment(candidate);
        }
        return candidate;
    }

    private static String part1(String oldPW) {
        return nextValidPassword(oldPW);
    }

    private static String part2(String oldPW) {
        return nextValidPassword(nextValidPassword(oldPW));
    }

    public static void main(String[] args) {
        try {
            String input = getInput();
            System.out.println("Input : \n" + input);
            System.out.println("Part 1 : " + part1(input));
            System.out.println("Part 2 : " + part2(input));
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
