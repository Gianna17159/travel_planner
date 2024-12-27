package sol;

import src.IBFS;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation for BFS, implements IBFS interface
 * @param <V> The type of the vertices
 * @param <E> The type of the edges
 */
public class BFS<V extends IVertex<E>, E extends IEdge<V>> implements IBFS<V, E> {

    @Override
    public List<E> getPath(IGraph<V, E> graph, V origin, V dest) {

        LinkedList<E> returningList = new LinkedList<>();
        LinkedList<V> checkList = new LinkedList<>();
        HashMap<V, E> edgeDict = new HashMap<>();
        HashSet<V> visitedList = new HashSet<>();

        checkList.add(origin);

        while(!(checkList.isEmpty())) {
            V cityToCheck = checkList.removeFirst();

            if (dest.equals(cityToCheck)) {
                E transToAdd = edgeDict.get(cityToCheck);
                while (transToAdd != null) {
                    returningList.addFirst(transToAdd);
                    transToAdd = edgeDict.get(graph.getEdgeSource(transToAdd));
                }
                return returningList;
            }
            else {
                visitedList.add(cityToCheck);
                for (E transport:graph.getOutgoingEdges(cityToCheck)) {
                    V targetCity = graph.getEdgeTarget(transport);
                    if (!(visitedList.contains(targetCity))) {
                        checkList.addLast(targetCity);
                        if (!edgeDict.containsKey(targetCity))
                            edgeDict.put(targetCity, transport);
                    }
                }
            }
        }
        return returningList;
    }
}
