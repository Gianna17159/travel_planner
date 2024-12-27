package test;

import org.junit.Test;
import sol.City;
import sol.Dijkstra;
import sol.Transport;
import sol.TravelGraph;
import src.IDijkstra;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static src.TransportType.*;
import static src.TransportType.TRAIN;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    private TravelGraph majorUSCitiesGraph;
    private City losAngeles;
    private City newYork;
    private City chicago;
    private City houston;
    private City phoenix;
    private City philadelphia;
    private City sanDiego;
    private City dallas;
    private City austin;
    private City jacksonville;

    private TravelGraph basicTestGraph;
    private City city1;
    private City city2;
    private City city3;
    private City city4;
    private City city5;
    private City city6;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    /**
     * creates the basicGraph values and network of edges
     */
    private void createBasicGraph(){
        this.basicTestGraph = new TravelGraph();

        this.city1 = new City("City 1");
        this.city2 = new City("City 2");
        this.city3 = new City("City 3");
        this.city4 = new City("City 4");
        this.city5 = new City("City 5");
        this.city6 = new City("City 6");

        this.basicTestGraph.addVertex(this.city1);
        this.basicTestGraph.addVertex(this.city2);
        this.basicTestGraph.addVertex(this.city3);
        this.basicTestGraph.addVertex(this.city4);
        this.basicTestGraph.addVertex(this.city5);
        this.basicTestGraph.addVertex(this.city6);

        this.basicTestGraph.addEdge(this.city1, new Transport(
                this.city1, this.city2, PLANE, 10000, 60));
        this.basicTestGraph.addEdge(this.city1, new Transport(
                this.city1, this.city6, TRAIN, 200, 120));
        this.basicTestGraph.addEdge(this.city1, new Transport(
                this.city1, this.city3, BUS, 150, 90));

        this.basicTestGraph.addEdge(this.city2, new Transport(
                this.city2, this.city1, TRAIN, 150, 90));
        this.basicTestGraph.addEdge(this.city2, new Transport(
                this.city2, this.city3, BUS, 80, 45));
        this.basicTestGraph.addEdge(this.city2, new Transport(
                this.city2, this.city4, PLANE, 120, 75));

        this.basicTestGraph.addEdge(this.city3, new Transport(
                this.city3, this.city2, BUS, 90, 50));
        this.basicTestGraph.addEdge(this.city3, new Transport(
                this.city3, this.city4, PLANE, 120, 75));
        this.basicTestGraph.addEdge(this.city3, new Transport(
                this.city3, this.city5, TRAIN, 160, 100));

        this.basicTestGraph.addEdge(this.city4, new Transport(
                this.city4, this.city3, PLANE, 130, 80));
        this.basicTestGraph.addEdge(this.city4, new Transport(
                this.city4, this.city5, TRAIN, 180, 110));
        this.basicTestGraph.addEdge(this.city4, new Transport(
                this.city4, this.city6, BUS, 100, 60));

        this.basicTestGraph.addEdge(this.city5, new Transport(
                this.city5, this.city4, TRAIN, 190, 115));
        this.basicTestGraph.addEdge(this.city5, new Transport(
                this.city5, this.city6, BUS, 70, 40));
        this.basicTestGraph.addEdge(this.city5, new Transport(
                this.city5, this.city1, PLANE, 140, 85));

        this.basicTestGraph.addEdge(this.city6, new Transport(
                this.city6, this.city5, BUS, 75, 42));
        this.basicTestGraph.addEdge(this.city6, new Transport(
                this.city6, this.city1, PLANE, 220, 130));
        this.basicTestGraph.addEdge(this.city6, new Transport(
                this.city6, this.city2, TRAIN, 170, 105));
    }

    /**
     * creates a graph of US cities connected by
     * 3 methods of travel, Bus, Plane and Train
     */
    private void makeUSCities(){
        this.majorUSCitiesGraph = new TravelGraph();

        this.losAngeles = new City("Los Angeles");
        this.newYork = new City("New York");
        this.chicago = new City("Chicago");
        this.houston = new City("Houston");
        this.phoenix = new City("Phoenix");
        this.philadelphia = new City("Philadelphia");
        this.sanDiego = new City("San Diego");
        this.dallas = new City("Dallas");
        this.austin = new City("Austin");
        this.jacksonville = new City("Jacksonville");

        this.majorUSCitiesGraph.addVertex(this.losAngeles);
        this.majorUSCitiesGraph.addVertex(this.newYork);
        this.majorUSCitiesGraph.addVertex(this.chicago);
        this.majorUSCitiesGraph.addVertex(this.houston);
        this.majorUSCitiesGraph.addVertex(this.phoenix);
        this.majorUSCitiesGraph.addVertex(this.philadelphia);
        this.majorUSCitiesGraph.addVertex(this.sanDiego);
        this.majorUSCitiesGraph.addVertex(this.dallas);
        this.majorUSCitiesGraph.addVertex(this.austin);
        this.majorUSCitiesGraph.addVertex(this.jacksonville);

        this.majorUSCitiesGraph.addEdge(this.losAngeles, new Transport(
                this.losAngeles, this.newYork, PLANE, 300, 200));
        this.majorUSCitiesGraph.addEdge(this.losAngeles, new Transport(
                this.losAngeles, this.chicago, TRAIN, 180, 100));
        this.majorUSCitiesGraph.addEdge(this.losAngeles, new Transport(
                this.losAngeles, this.philadelphia, BUS, 300, 80));

        this.majorUSCitiesGraph.addEdge(this.newYork, new Transport(
                this.newYork, this.chicago, TRAIN, 120, 80));
        this.majorUSCitiesGraph.addEdge(this.newYork, new Transport(
                this.newYork, this.houston, PLANE, 240, 250));
        this.majorUSCitiesGraph.addEdge(this.newYork, new Transport(
                this.newYork, this.phoenix, BUS, 420, 150));

        this.majorUSCitiesGraph.addEdge(this.chicago, new Transport(
                this.chicago, this.houston, PLANE, 240, 200));
        this.majorUSCitiesGraph.addEdge(this.chicago, new Transport(
                this.chicago, this.philadelphia, TRAIN, 180, 100));
        this.majorUSCitiesGraph.addEdge(this.chicago, new Transport(
                this.chicago, this.sanDiego, BUS, 400, 120));

        this.majorUSCitiesGraph.addEdge(this.houston, new Transport(
                this.houston, this.phoenix, PLANE, 180, 180));
        this.majorUSCitiesGraph.addEdge(this.houston, new Transport(
                this.houston, this.dallas, BUS, 120, 40));
        this.majorUSCitiesGraph.addEdge(this.houston, new Transport(
                this.houston, this.austin, TRAIN, 90, 30));

        this.majorUSCitiesGraph.addEdge(this.philadelphia, new Transport(
                this.philadelphia, this.sanDiego, PLANE, 300, 300));
        this.majorUSCitiesGraph.addEdge(this.philadelphia, new Transport(
                this.philadelphia, this.austin, BUS, 400, 150));
        this.majorUSCitiesGraph.addEdge(this.philadelphia, new Transport(
                this.philadelphia, this.jacksonville, TRAIN, 240, 120));

        this.majorUSCitiesGraph.addEdge(this.sanDiego, new Transport(
                this.sanDiego, this.dallas, PLANE, 300, 250));
        this.majorUSCitiesGraph.addEdge(this.sanDiego, new Transport(
                this.sanDiego, this.austin, TRAIN, 180, 100));
        this.majorUSCitiesGraph.addEdge(this.sanDiego, new Transport(
                this.sanDiego, this.jacksonville, BUS, 720, 200));

        this.majorUSCitiesGraph.addEdge(this.dallas, new Transport(
                this.dallas, this.austin, BUS, 120, 20));
        this.majorUSCitiesGraph.addEdge(this.dallas, new Transport(
                this.dallas, this.jacksonville, PLANE, 300, 300));
        this.majorUSCitiesGraph.addEdge(this.dallas, new Transport(
                this.dallas, this.phoenix, TRAIN, 240, 120));

        this.majorUSCitiesGraph.addEdge(this.austin, new Transport(
                this.austin, this.jacksonville, TRAIN, 180, 100));
        this.majorUSCitiesGraph.addEdge(this.austin, new Transport(
                this.austin, this.phoenix, BUS, 300, 150));
        this.majorUSCitiesGraph.addEdge(
                this.austin, new Transport(this.austin, this.houston, PLANE, 60, 50));

        this.majorUSCitiesGraph.addEdge(this.jacksonville, new Transport(
                this.jacksonville, this.phoenix, TRAIN, 400, 200));
        this.majorUSCitiesGraph.addEdge(
                this.jacksonville, new Transport(this.jacksonville, this.philadelphia, BUS, 420, 200));
        this.majorUSCitiesGraph.addEdge(
                this.jacksonville, new Transport(this.jacksonville, this.houston, PLANE, 240, 180));
    }

    /**
     * A sample test that tests Dijkstra's on a simple graph. Checks that the shortest path using Dijkstra's is what we
     * expect.
     */
    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalc = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalc);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalc);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    /**
     * test for the basicGraph with minute calculations
     */
    @Test
    public void testBasicMinutes(){
        this.createBasicGraph();
        IDijkstra<City,Transport> dijkstra = new Dijkstra<>();
        List<Transport> path;

        Function<Transport, Double> minutesCalc = Transport::getMinutes;

        //Case 1: city1 to city2, checking total minutes and path length
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city1, this.city2, minutesCalc);
        assertEquals(path.size(), 1);
        int totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 60);

        //Case 2: city1 to city5, checking total minutes and path length
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city1, this.city5, minutesCalc);
        assertEquals(path.size(), 2);
        totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 162);

        //Case 3: city4 to city2, checking total minutes and path length
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city4, this.city2, minutesCalc);
        assertEquals(path.size(), 2);
        totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 130);

        //Case 4: city3 to city3, checking total minutes and path length,
        // should be 0 because they are the same
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city3, this.city3, minutesCalc);
        assertEquals(path.size(), 0);
        totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 0);
    }

    /**
     * test for the basicGraph with cost calculations
     */
    @Test
    public void testBasicCost(){
        this.createBasicGraph();
        IDijkstra<City,Transport> dijkstra = new Dijkstra<>();
        List<Transport> path;

        Function<Transport, Double> costCalc = Transport::getPrice;

        //Case 1: city1 to city2, checking total minutes and path length
        //using this example because they are neighbors but
        //1 to 2 has been changed to be very expensive
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city1, this.city2, costCalc);
        assertEquals(path.size(), 2);
        int totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 240);

        //Case 2: city1 to city5, checking total minutes and path length
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city1, this.city5, costCalc);
        assertEquals(path.size(), 2);
        totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 275);

        //Case 3: city4 to city2, checking total minutes and path length
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city4, this.city2, costCalc);
        assertEquals(path.size(), 2);
        totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 220);

        //Case 4: city3 to city3, checking total minutes and path length,
        // should be 0 because they are the same
        path = dijkstra.getShortestPath(this.basicTestGraph, this.city3, this.city3, costCalc);
        assertEquals(path.size(), 0);
        totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 0);
    }

    /**
     * test for the majorUSCitiesGraph with cost calculations
     */
    @Test
    public void testUSCost(){
        this.makeUSCities();
        IDijkstra<City, Transport> dijkstra = new Dijkstra<>();
        Function<Transport, Double> costCalc = Transport::getPrice;
        List<Transport> path;

        //Case 1: Los Angeles to Dallas, normal case, minimal cost and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.losAngeles, this.dallas, costCalc);
        assertEquals(path.size(),3);
        int totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 540);

        //Case 2: Austin to New York, non-connecting case, minimal cost and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.austin, this.newYork, costCalc);
        assertEquals(path.size(),0); //the cities do not connect
        totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 0);

        //Case 3: Los Angeles to New York, single flight case, minimal cost and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.losAngeles, this.newYork, costCalc);
        assertEquals(path.size(),1); //the cities do not connect
        totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 300);

        //Case 4: Los Angeles to Los Angeles, same city case, minimal cost and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.losAngeles, this.losAngeles, costCalc);
        assertEquals(path.size(),0);
        totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 0);

        //Case 3: Los Angeles to Philadelphia, normal case, minimal cost and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.losAngeles, this.philadelphia, costCalc);
        assertEquals(path.size(),1);
        totalCost = 0;
        for(Transport trans: path){
            totalCost += trans.getPrice();
        }
        assertEquals(totalCost, 300);
    }

    /**
     * test for the majorUSCitiesGraph with minutes calculations
     */
    @Test
    public void testUSMinutes(){
        this.makeUSCities();
        IDijkstra<City, Transport> dijkstra = new Dijkstra<>();
        Function<Transport, Double> minutesCalc = Transport::getMinutes;
        List<Transport> path;

        //Case 1: Los Angeles to Dallas, longer path case, minimal minutes and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.losAngeles, this.dallas, minutesCalc);
        assertEquals(path.size(),4);
        int totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 320);

        //Case 2: Austin to New York, non-connecting case, minimal minutes and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.austin, this.newYork, minutesCalc);
        assertEquals(path.size(),0); //the cities do not connect
        totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 0);

        //Case 3: Los Angeles to New York, single flight case, minimal minutes and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.losAngeles, this.newYork, minutesCalc);
        assertEquals(path.size(),1); //the cities do not connect
        totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 200);

        //Case 4: New York to Austin, mid-length path case, minimal minutes and path length
        path = dijkstra.getShortestPath(this.majorUSCitiesGraph, this.newYork, this.austin, minutesCalc);
        assertEquals(path.size(), 2);
        totalMins = 0;
        for(Transport trans: path){
            totalMins += trans.getMinutes();
        }
        assertEquals(totalMins, 280);
    }
}
