package test;

import org.junit.Test;
import sol.City;
import sol.Transport;
import sol.TravelGraph;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static src.TransportType.*;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

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

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
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
     * Sample test for the graph. Tests that the number of vertices and the vertices in the graph are what we expect.
     */
    @Test
    public void testGetVertices() {
        this.createSimpleGraph();

        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

    /**
     * test addVertex
     */
    @Test
    public void testAddVertex(){
        this.makeBasicGraph();

        assertEquals(this.basicTestGraph.getVertices().size(), 6);
        City cityToAdd = new City("city to add");
        this.basicTestGraph.addVertex(cityToAdd);

        assertEquals(this.basicTestGraph.getVertices().size(), 7);
    }

    /**
     * test addEdge
     */
    @Test
    public void testAddEdge(){
        this.makeBasicGraph();

        assertEquals(this.city1.getOutgoing().size(), 3);
        List<City> cityList = new LinkedList<>();
        for(Transport transport: this.city1.getOutgoing()){
            cityList.add(transport.getTarget());
        }
        assertFalse(cityList.contains(this.city5));
        this.basicTestGraph.addEdge(this.city1, new Transport(this.city1, this.city5, BUS, 10, 10));
        assertEquals(this.city1.getOutgoing().size(), 4);
        for(Transport transport: this.city1.getOutgoing()){
            cityList.add(transport.getTarget());
        }
        assertTrue(cityList.contains(this.city5));
    }
    /**
     * test the getVertices method
     */
    @Test
    public void testBetterGetVertices(){
        this.makeBasicGraph();

        assertTrue(this.basicTestGraph.getVertices().contains(this.city1));
        assertTrue(this.basicTestGraph.getVertices().contains(this.city2));
        assertTrue(this.basicTestGraph.getVertices().contains(this.city3));
        assertTrue(this.basicTestGraph.getVertices().contains(this.city4));
        assertTrue(this.basicTestGraph.getVertices().contains(this.city5));
        assertTrue(this.basicTestGraph.getVertices().contains(this.city6));

        City notInList = new City("Not In List");
        assertFalse(this.basicTestGraph.getVertices().contains(notInList));

        assertEquals(this.basicTestGraph.getVertices().size(), 6);
    }

    /**
     * testing getEdgeSource
     */
    @Test
    public void testGetEdgeSource(){
        this.makeBasicGraph();

        for(Transport edge:this.basicTestGraph.getOutgoingEdges(this.city1)){
            assert(this.basicTestGraph.getEdgeSource(edge).equals(this.city1));
        }

        for(Transport edge:this.basicTestGraph.getOutgoingEdges(this.city2)){
            assertNotEquals(this.basicTestGraph.getEdgeSource(edge), this.city1);
        }
    }

    /**
     * testing getEdgeTarget
     */
    @Test
    public void testGetEdgeTarget(){
        this.makeBasicGraph();

        List<City> expectedCity1 = new LinkedList<>();
        expectedCity1.add(this.city2);
        expectedCity1.add(this.city6);
        expectedCity1.add(this.city3);

        for(Transport edge:this.basicTestGraph.getOutgoingEdges(this.city1)){
            assertTrue(expectedCity1.contains(this.basicTestGraph.getEdgeTarget(edge)));
        }

        Transport testEdge = new Transport(this.city1, this.city1, BUS, 0,0);
        assertEquals(this.basicTestGraph.getEdgeTarget(testEdge), this.city1);
        assertNotEquals(this.basicTestGraph.getEdgeTarget(testEdge), this.city3);
    }

    /**
     * testing getOutgoingEdges
     */
    @Test
    public void testGetOutgoingEdges(){
        this.makeBasicGraph();

        assertEquals(this.basicTestGraph.getOutgoingEdges(this.city1).size(),3);
        Transport testEdge = new Transport(this.city1, this.city1, BUS, 0,0);
        this.basicTestGraph.addEdge(this.city1, testEdge);
        assertEquals(this.basicTestGraph.getOutgoingEdges(this.city1).size(),4);
        assertTrue(this.basicTestGraph.getOutgoingEdges(this.city1).contains(testEdge));
    }

    /**
     * testing for searchByName
     */
    @Test
    public void testSearchByName(){
        this.makeBasicGraph();

        assertEquals(this.basicTestGraph.searchByName("City 1"), this.city1);
        assertEquals(this.basicTestGraph.searchByName("City 2"), this.city2);
        assertEquals(this.basicTestGraph.searchByName("City 3"), this.city3);
        assertEquals(this.basicTestGraph.searchByName("City 4"), this.city4);
        assertEquals(this.basicTestGraph.searchByName("City 5"), this.city5);
        assertEquals(this.basicTestGraph.searchByName("City 6"), this.city6);
        assertNull(this.basicTestGraph.searchByName("City 100"));
    }
}
