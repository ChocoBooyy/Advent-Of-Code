package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Solution {
    private static String getInput() throws IOException {
        Path path = Paths.get("input.txt");
        return Files.readString(path);
    }

    private static String lookAndSay(String input) {
        StringBuilder res = new StringBuilder();

        char prevChar =  input.charAt(0);
        int count = 1;

        for(int i =  1; i < input.length(); i++) {
            char currChar = input.charAt(i);

            if(currChar == prevChar) {
                count++;
            } else {
                res.append(count).append(prevChar);
                prevChar = currChar;
                count = 1;
            }
        }

        res.append(count).append(prevChar);

        return res.toString();
    }

    private static int part1(String input) {
        String res = input;

        for(int i = 0; i < 40; i++) {
            res = lookAndSay(res);
        }

        System.out.println("40 times: " + res);

        return res.length();
    }

    private static int part2(String input) {
        String res = "";


        for(int i = 0; i < 50; i++) {
            if(i == 0){
                res = lookAndSay(input);
            } else {
                res = lookAndSay(res);
            }
        }

        System.out.println("50 times: " + res);

        return res.length();
    }

    public static void main(String[] args) {
        try {
            String input = getInput();
            System.out.println("Input : \n" + input);
            System.out.println("Once : " + lookAndSay(input));
            int part1 =  part1(input);
            int part2 = part2(input);
            System.out.println("Part 1 : " + part1);
            System.out.println("Part 2 : " + part2);
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
