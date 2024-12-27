package sol;

import src.IDijkstra;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;
import java.util.function.Function;

/**
 * Implementation for Dijkstra's algorithm
 *
 * @param <V> the type of the vertices
 * @param <E> the type of the edges
 */
public class Dijkstra<V extends IVertex<E>, E extends IEdge<V>> implements IDijkstra<V, E> {

    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        HashMap<V,E> cameFrom = new HashMap<>();
        Set<V> allCities = graph.getVertices();
        HashMap<V,Double> cityDistances = new HashMap<V, Double>();
        for(V city:allCities){
            if(city.equals(source)){
                cityDistances.put(city,0.0);
            }
            else{
                cityDistances.put(city, Double.MAX_VALUE);
            }
        }

        Comparator<V> distanceComparator = (City1, City2) -> {
            return Double.compare(cityDistances.get(City1), cityDistances.get(City2));
        };

        PriorityQueue<V> checkQueue = new PriorityQueue<>(distanceComparator);
        checkQueue.addAll(allCities);

        while(!(checkQueue.isEmpty())){
            V checkingCity = checkQueue.poll();
            for(E transport:graph.getOutgoingEdges(checkingCity)) {
                V targetCity = graph.getEdgeTarget(transport);

                Double distToNext = cityDistances.get(targetCity);
                if ((cityDistances.get(checkingCity) + edgeWeight.apply(transport)) < distToNext) {
                    distToNext = (cityDistances.get(checkingCity) + edgeWeight.apply(transport));
                    checkQueue.remove(targetCity);
                    cityDistances.replace(targetCity, distToNext);
                    cameFrom.put(targetCity, transport);
                    checkQueue.add(targetCity);
                }
            }
        }
        LinkedList<E> returningList = new LinkedList<>();
        E transport = cameFrom.get(destination);
        while(transport != null){
            returningList.addFirst(transport);
            transport = cameFrom.get(transport.getSource());
        }
        return returningList;
    }
}
