package test;

import org.junit.Test;
import sol.BFS;
import sol.Transport;
import sol.TravelGraph;
import sol.City;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static src.TransportType.*;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 */
public class BFSTest {

    private static final double DELTA = 001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;

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

    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    /**
     * creates the basicGraph values and network of edges
     */
    private void makeBasicGraph(){
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
                this.city1, this.city2, PLANE, 100, 60));
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
                this.losAngeles, this.houston, BUS, 300, 80));

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
     * A sample test that tests BFS on a simple graph. Checks that running BFS gives us the path we expect.
     */
    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200, DELTA);
        assertEquals(path.size(), 2);

        //Case 1: tests a 2 step link
        path = bfs.getPath(this.graph, this.a, this.c);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 2, DELTA);
        assertEquals(path.size(), 2);

        //Case 2: Tests a direct link
        path = bfs.getPath(this.graph, this.a, this.b);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 1, DELTA);
        assertEquals(path.size(), 1);
    }

    /**
     * tests example graph of 6 cities connected in a circle
     * with some connections crossing the middle as well
     */
    @Test
    public void setBasicGraphFS(){
        this.makeBasicGraph();
        BFS<City, Transport> bfs = new BFS<>();
        List<Transport> path;

        // Test Case 1: City 1 to City 2
        path = bfs.getPath(this.basicTestGraph, this.city1, this.city2);
        assertEquals(1, path.size());

        // Case 2: City 1 to City 4
        path = bfs.getPath(this.basicTestGraph, this.city1, this.city4);
        assertEquals(2, path.size());

        // Case 3: City 1 to City 6
        path = bfs.getPath(this.basicTestGraph, this.city1, this.city5);
        assertEquals(2, path.size());

        // Case 4: City 2 to City 3
        path = bfs.getPath(this.basicTestGraph, this.city2, this.city3);
        assertEquals(1, path.size());

        // Case 5: City 3 to City 4
        path = bfs.getPath(this.basicTestGraph, this.city3, this.city6);
        assertEquals(2, path.size());

        // Case 6: Check that every city is reachable from every other city as expected
        for(City city: this.basicTestGraph.getVertices()) {
            for (City otherCity : this.basicTestGraph.getVertices()) {
                if(!(city.equals(otherCity))) {
                    path = bfs.getPath(this.basicTestGraph, city, otherCity);
                    assert (path.size() != 0);
                }
            }
        }
    }

    /**
     * tests example graph of US cities connecting
     * tests 7 cases, ranging from 0 for no connection to the largest value of 3
     */
    @Test
    public void testUSCitiesBFS(){
        this.makeUSCities();

        BFS<City, Transport> bfs = new BFS<>();
        List<Transport> path;

        // Case 1: Los Angeles to New York
        path = bfs.getPath(this.majorUSCitiesGraph, this.losAngeles, this.newYork);
        assertEquals(1, path.size());

        // Case 2: Chicago to Houston
        path = bfs.getPath(this.majorUSCitiesGraph, this.chicago, this.houston);
        assertEquals(1, path.size());

        // Case 3: San Diego to New York (no connection so result is 0)
        path = bfs.getPath(this.majorUSCitiesGraph, this.sanDiego, this.newYork);
        assertEquals(0, path.size());

        // Case 4: Houston to Philadelphia
        path = bfs.getPath(this.majorUSCitiesGraph, this.houston, this.philadelphia);
        assertEquals(3, path.size());

        // Case 5: Austin to Phoenix
        path = bfs.getPath(this.majorUSCitiesGraph, this.austin, this.phoenix);
        assertEquals(1, path.size());

        // Case 6: Jacksonville to San Diego
        path = bfs.getPath(this.majorUSCitiesGraph, this.jacksonville, this.sanDiego);
        assertEquals(2, path.size());

        // Case 7: Los Angeles to San Diego
        path = bfs.getPath(this.majorUSCitiesGraph, this.losAngeles, this.sanDiego);
        assertEquals(2, path.size());
    }
}
