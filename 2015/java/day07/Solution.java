package day07;

import common.InputFiles;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    private static List<String> getInput(boolean example) throws IOException {
        return InputFiles.readNonEmptyLines(Solution.class, example);
    }

    private static TreeMap<String, Integer> evaluateCircuit(List<String> lines, TreeMap<String, Integer> override) {
        TreeMap<String, Integer> wires = new TreeMap<>();

        if (override != null) {
            wires.putAll(override);
        }

        final Pattern assignP = Pattern.compile("^\\s*(\\w+)\\s*->\\s*(\\w+)\\s*$");
        final Pattern notP    = Pattern.compile("^\\s*NOT\\s+(\\w+)\\s*->\\s*(\\w+)\\s*$");
        final Pattern binP    = Pattern.compile("^\\s*(\\w+)\\s+(AND|OR|LSHIFT|RSHIFT)\\s+(\\w+)\\s*->\\s*(\\w+)\\s*$",
                Pattern.CASE_INSENSITIVE);

        List<String> remaining = new ArrayList<>(lines);
        while (!remaining.isEmpty()) {
            Iterator<String> it = remaining.iterator();
            boolean progress = false;

            while (it.hasNext()) {
                String line = it.next();
                boolean executed = false;

                Matcher m;

                // Assignment
                m = assignP.matcher(line);
                if (m.matches()) {
                    String left = m.group(1);
                    String out = m.group(2);
                    if (wires.containsKey(out) && override != null && override.containsKey(out)) {
                        executed = true; // keep overridden value
                    } else {
                        Integer val = resolveToken(left, wires);
                        if (val != null) {
                            wires.put(out, val & 0xFFFF);
                            executed = true;
                        }
                    }
                }

                // NOT
                m = notP.matcher(line);
                if (!executed && m.matches()) {
                    String inTok = m.group(1);
                    String out = m.group(2);
                    Integer val = resolveToken(inTok, wires);
                    if (val != null) {
                        wires.put(out, (~val) & 0xFFFF);
                        executed = true;
                    }
                }

                // Binary ops
                m = binP.matcher(line);
                if (!executed && m.matches()) {
                    String leftTok = m.group(1);
                    String op = m.group(2).toUpperCase();
                    String rightTok = m.group(3);
                    String out = m.group(4);

                    Integer lv = resolveToken(leftTok, wires);
                    Integer rv = resolveToken(rightTok, wires);

                    if (lv != null && rv != null) {
                        int result;
                        switch (op) {
                            case "AND" -> result = lv & rv;
                            case "OR" -> result = lv | rv;
                            case "LSHIFT" -> result = lv << rv;
                            case "RSHIFT" -> result = lv >>> rv;
                            default -> throw new IllegalArgumentException("Unknown operator: " + op);
                        }
                        wires.put(out, result & 0xFFFF);
                        executed = true;
                    }
                }

                if (executed) {
                    it.remove();
                    progress = true;
                }
            }

            if (!progress) {
                System.err.println("Cannot resolve remaining lines, circular dependency or missing inputs.");
                break;
            }
        }

        return wires;
    }

    private static Integer resolveToken(String tok, TreeMap<String, Integer> wires) {
        if (tok.matches("\\d+")) {
            return Integer.parseInt(tok);
        } else {
            return wires.get(tok);
        }
    }

    private static void part1(List<String> lines) {
        TreeMap<String, Integer> wires = evaluateCircuit(lines, null);
        System.out.println("=== Part 1: wire a = " + wires.get("a"));
    }

    private static void part2(List<String> lines) {
        // Evaluate part 1 first to get the value of wire 'a'
        TreeMap<String, Integer> wiresPart1 = evaluateCircuit(lines, null);
        Integer aSignal = wiresPart1.get("a");

        // Override wire 'b' with signal from 'a', reset all other wires
        TreeMap<String, Integer> override = new TreeMap<>();
        override.put("b", aSignal);

        TreeMap<String, Integer> wiresPart2 = evaluateCircuit(lines, override);
        System.out.println("=== Part 2: wire a = " + wiresPart2.get("a"));
    }

    public static void main(String[] args) {
        try {
            List<String> input = getInput(true);
            System.out.println("Input : \n" + input);

            System.out.println("=== Final Results ===");
            part1(input);
            part2(input);
        } catch (IOException e) {
            System.err.println("Failed to read input: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
