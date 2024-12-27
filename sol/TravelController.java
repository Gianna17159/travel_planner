package sol;

import src.IDijkstra;
import src.ITravelController;
import src.TransportType;
import src.TravelCSVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation for TravelController
 */
public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;

    /**
     * Constructor for TravelController
     */
    public TravelController() {
    }

    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null;
        };

        Function<Map<String, String>, Void> connectionBuilder = inputMap -> {
            City origin = this.graph.searchByName(inputMap.get("origin"));
            this.graph.addEdge(origin,
                    new Transport(
                            origin,
                            this.graph.searchByName(inputMap.get("destination")),
                            TransportType.fromString(inputMap.get("type")),
                            Double.parseDouble(inputMap.get("price")),
                            Double.parseDouble(inputMap.get("duration")))
                    );
            return null;
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }
        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseTransportation(transportFile, connectionBuilder);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }
        //returns a success message if the error isn't caught
        return "Successfully loaded cities and transportation files.";
    }

    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Function<Transport, Double> findEdgeWeight = transport -> {
            return transport.getMinutes();
        };

        IDijkstra<City, Transport> iDijkstraInstance = new Dijkstra<>();



        return iDijkstraInstance.getShortestPath(this.graph,
                this.graph.searchByName(source),
                this.graph.searchByName(destination),
                findEdgeWeight);
    }

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Function<Transport, Double> findEdgeWeight = transport -> {
            return transport.getPrice();
        };

        IDijkstra<City, Transport> iDijkstraInstance = new Dijkstra<>();



        return iDijkstraInstance.getShortestPath(this.graph,
                this.graph.searchByName(source),
                this.graph.searchByName(destination),
                findEdgeWeight);
    }

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS<City, Transport> bfs = new BFS<>();
        return bfs.getPath(this.graph,
                this.graph.searchByName(source),
                this.graph.searchByName(destination));
    }

    /**
     * Returns the graph stored by the controller
     *
     * NOTE: You __should not__ be using this in your implementation, this is purely meant to be used for the visualizer
     *
     * @return The TravelGraph currently stored in the TravelController
     */
    public TravelGraph getGraph() {
        return this.graph;
    }
}
