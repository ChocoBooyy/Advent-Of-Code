package Day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

public class Solution {
    private static List<String> getInput() throws IOException {
        Path path = Paths.get("input.txt");
        List<String> lines = Files.readAllLines(path);
        return List.of(lines.toArray(new String[0]));
    }

    private static Map<String, City> getCityMap(List<String> lines) {
        Map<String, City> cityMap = new HashMap<>();
        Pattern pattern = Pattern.compile("([a-zA-Z]+)\\s+to\\s+([a-zA-Z]+)\\s*=\\s*(\\d+)");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String name1 = matcher.group(1);
                String name2 = matcher.group(2);
                int distance = Integer.parseInt(matcher.group(3));

                // Create or get cities
                City city1 = cityMap.computeIfAbsent(name1, City::new);
                City city2 = cityMap.computeIfAbsent(name2, City::new);

                // Add bidirectional connection
                city1.addNeighbor(city2, distance);
                city2.addNeighbor(city1, distance);
            }
        }

        return cityMap;
    }

    private static Map<String, Integer> getAllRoutes(List<String> lines) {
        Map<String, City> cityMap = getCityMap(lines);

        List<City> cities = new ArrayList<>(cityMap.values());
        List<List<City>> allRoutes = permutations(cities);

        Map<String, Integer> routes = new LinkedHashMap<>(); // preserves insertion order

        for (List<City> route : allRoutes) {
            int totalDistance = 0;
            boolean validRoute = true;

            for (int i = 0; i < route.size() - 1; i++) {
                City current = route.get(i);
                City next = route.get(i + 1);
                Integer dist = current.neighbors.get(next);

                if (dist == null) {
                    validRoute = false;
                    break;
                }
                totalDistance += dist;
            }

            if (validRoute) {
                String path = String.join(" -> ",
                        route.stream().map(c -> c.name).toList());
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
            List<String> input = getInput();
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
