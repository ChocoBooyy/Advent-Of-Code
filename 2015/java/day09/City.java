package Day09;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class City {
    String name;
    Map<City, Integer> neighbors = new HashMap<>();

    City(String name) {
        this.name = name;
    }

    void addNeighbor(City city, int distance) {
        neighbors.put(city, distance);
    }

    @Override
    public String toString() {
        // Format: CityName -> [Neighbor1(distance), Neighbor2(distance)]
        String neighborsStr = neighbors.entrySet().stream()
                .map(entry -> entry.getKey().name + "(" + entry.getValue() + ")")
                .collect(Collectors.joining(", "));

        return name + " -> [" + neighborsStr + "]";
    }
}
