package day04;

import common.InputFiles;
import java.io.IOException;
import java.security.MessageDigest;

public class Solution {
    private static String getInput(boolean example) throws IOException {
        return InputFiles.readString(Solution.class, example);
    }

    private static boolean ismd5four0(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(str.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            String md5Hash = sb.toString();
            System.out.println(md5Hash);

            return md5Hash.startsWith("00000");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean ismd6four0(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(str.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            String md5Hash = sb.toString();
            System.out.println(md5Hash);

            return md5Hash.startsWith("000000");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int part1(String input) {
        String candidate = "";
        int num = 1;

        while (!(ismd5four0(candidate))) {
            candidate = input + num;
            num++;
        }

        System.out.println("\nPart 1: " + candidate + "\n");

        return num-1;
    }

    private static int part2(String input) {
        String candidate = "";
        int num = 1;

        while (!(ismd6four0(candidate))) {
            candidate = input + num;
            num++;
        }

        System.out.println("\nPart 1: " + candidate + "\n");

        return num-1;
    }

    public static void main(String[] args) {
        try {
            String input = getInput(true);
            System.out.println("Input : \n" + input);
            int res1 = part1(input);
            int res2 = part2(input);

            System.out.println("=== Final Results ===");
            System.out.println("Part 1: " + res1);
            System.out.println("Part 2: " + res2);
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
