package day04;

import common.InputFiles;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SolutionMultithread {

    private static String getInput(boolean example) throws IOException {
        return InputFiles.readString(SolutionMultithread.class, example).trim();
    }

    // Generic MD5 checker for a given number of leading zeros
    private static boolean checkMD5(String str, int zeros) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(str.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) sb.append(String.format("%02x", b));

            String md5Hash = sb.toString();
            // Uncomment to see all hashes (slow)
            // System.out.println(md5Hash);

            return md5Hash.startsWith("0".repeat(zeros));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Multithreaded AdventCoin miner
    private static int mineAdventCoin(String input, int zeros) throws InterruptedException {
        final int THREADS = Runtime.getRuntime().availableProcessors(); // number of threads
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger resultNumber = new AtomicInteger(-1);

        Thread[] threads = new Thread[THREADS];

        for (int t = 0; t < THREADS; t++) {
            final int threadId = t;
            threads[t] = new Thread(() -> {
                int num = threadId + 1;
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    while (!found.get()) {
                        String candidate = input + num;
                        byte[] hashBytes = md.digest(candidate.getBytes());

                        StringBuilder sb = new StringBuilder();
                        for (byte b : hashBytes) sb.append(String.format("%02x", b));

                        if (sb.toString().startsWith("0".repeat(zeros))) {
                            resultNumber.set(num);
                            found.set(true);
                            break;
                        }

                        num += THREADS; // skip numbers for this thread
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads[t].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) thread.join();

        return resultNumber.get();
    }

    private static int part1(String input) throws InterruptedException {
        int num = mineAdventCoin(input, 5);
        System.out.println("Part 1 found number: " + num);
        return num;
    }

    private static int part2(String input) throws InterruptedException {
        int num = mineAdventCoin(input, 6);
        System.out.println("Part 2 found number: " + num);
        return num;
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
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
