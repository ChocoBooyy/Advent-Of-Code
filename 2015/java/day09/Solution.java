package day09;

import common.InputFiles;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

public class Solution {
    private static List<String> getInput(boolean example) throws IOException {
        return InputFiles.readNonEmptyLines(Solution.class, example);
    }

    private static Map<String, Map<String, Integer>> getCityMap(List<String> lines) {
        Map<String, Map<String, Integer>> cityMap = new HashMap<>();
        Pattern pattern = Pattern.compile("([a-zA-Z]+)\\s+to\\s+([a-zA-Z]+)\\s*=\\s*(\\d+)");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String name1 = matcher.group(1);
                String name2 = matcher.group(2);
                int distance = Integer.parseInt(matcher.group(3));

                cityMap.computeIfAbsent(name1, k -> new HashMap<>()).put(name2, distance);
                cityMap.computeIfAbsent(name2, k -> new HashMap<>()).put(name1, distance);
            }
        }

        return cityMap;
    }

    private static Map<String, Integer> getAllRoutes(List<String> lines) {
        Map<String, Map<String, Integer>> cityMap = getCityMap(lines);

        List<String> cities = new ArrayList<>(cityMap.keySet());
        List<List<String>> allRoutes = permutations(cities);

        Map<String, Integer> routes = new LinkedHashMap<>(); // preserves insertion order

        for (List<String> route : allRoutes) {
            int totalDistance = 0;
            boolean validRoute = true;

            for (int i = 0; i < route.size() - 1; i++) {
                String current = route.get(i);
                String next = route.get(i + 1);
                Integer dist = cityMap.getOrDefault(current, Map.of()).get(next);

                if (dist == null) {
                    validRoute = false;
                    break;
                }
                totalDistance += dist;
            }

            if (validRoute) {
                String path = String.join(" -> ", route);
                routes.put(path, totalDistance);
            }
        }

        return routes;
    }

    // Generate all permutations of cities
    private static <T> List<List<T>> permutations(List<T> items) {
        if (items.isEmpty()) {
            return List.of(List.of());
        }

        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            T current = items.get(i);
            List<T> remaining = new ArrayList<>(items);
            remaining.remove(i);
            for (List<T> perm : permutations(remaining)) {
                List<T> newPerm = new ArrayList<>();
                newPerm.add(current);
                newPerm.addAll(perm);
                result.add(newPerm);
            }
        }
        return result;
    }

    private static int part1(List<String> lines) {
        Map<String, Integer> routes = getAllRoutes(lines);

        // Print all stored routes
        routes.forEach((path, distance) ->
                System.out.println(path + " = " + distance));

        // Example: find shortest and longest
        int min = routes.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)   // extract the distance
                .orElseThrow();             // handle empty case

        return min;
    }

    private static int part2(List<String> lines) {
        Map<String, Integer> routes = getAllRoutes(lines);

        // Print all stored routes
        routes.forEach((path, distance) ->
                System.out.println(path + " = " + distance));

        // Example: find shortest and longest
        int max = routes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)   // extract the distance
                .orElseThrow();             // handle empty case

        return max;
    }

    public static void main(String[] args) {
        try {
            List<String> input = getInput(true);
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
