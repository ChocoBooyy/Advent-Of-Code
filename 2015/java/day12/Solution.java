package day12;

import common.InputFiles;
import java.io.IOException;
import java.util.regex.*;

public class Solution {
    private static String getInput(boolean example) throws IOException {
        return InputFiles.readString(Solution.class, example);
    }

    private static int allSumNum(String input){
        int res = 0;
        StringBuilder num = new StringBuilder();

        for(int i = 0; i < input.length(); i++) {
            if(!(Character.isDigit(input.charAt(i))) && !(num.isEmpty())) {
                res += Integer.parseInt(num.toString());
                num = new StringBuilder();
            } else {
                if(Character.isDigit(input.charAt(i))) {
                    if(input.charAt(i-1) == '-') {
                        num.append("-").append(input.charAt(i));
                    } else {
                        num.append(input.charAt(i));
                    }
                }
            }
        }

        return res;
    }

    private static int part1(String input) {
        return allSumNum(input);
    }


    private static int part2(String input) {
        if (input == null || input.isEmpty()) return 0;
        String s = input;

        Pattern innerObj = Pattern.compile("\\{[^{}]*\\}");
        Pattern redValue = Pattern.compile(":\\s*\"red\"\\s*(,|})");

        while (true) {
            Matcher m = innerObj.matcher(s);
            StringBuffer sb = new StringBuffer();
            boolean found = false;

            while (m.find()) {
                found = true;
                String obj = m.group();
                if (redValue.matcher(obj).find()) {
                    m.appendReplacement(sb, "");
                } else {
                    String inner = obj.substring(1, obj.length() - 1);
                    m.appendReplacement(sb, Matcher.quoteReplacement(inner));
                }
            }

            if (!found) break;
            m.appendTail(sb);
            s = sb.toString();
        }

        return allSumNum(s);
    }

    public static void main(String[] args) {
        try {
            String input = getInput(true);
            System.out.println("Input : \n" + input);
            System.out.println("Part 1 : " + part1(input));
            System.out.println("Part 2 : " + part2(input));
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
