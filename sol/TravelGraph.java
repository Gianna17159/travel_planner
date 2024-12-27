package sol;

import src.IGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for TravelGraph
 */
public class TravelGraph implements IGraph<City, Transport> {
    private HashSet<City> allCities;

    public TravelGraph() {
        this.allCities = new HashSet<>();
    }

    @Override
    public void addVertex(City vertex) {
        this.allCities.add(vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        for (City c : this.allCities) {
            if (c.equals(origin)) {
                c.addOut(edge);
            }
        }
    }

    @Override
    public Set<City> getVertices() {
        return this.allCities;
    }

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    /**
     * takes in a string parameter and iterates
     * through the set of all cities, checking
     * if the name is the same as the city name
     * @param name
     * @return
     */
    public City searchByName(String name) {
        for (City c : this.allCities) {
            if (c.toString().equals(name)) {
                return c;
            }
        }
        return null;
    }
}