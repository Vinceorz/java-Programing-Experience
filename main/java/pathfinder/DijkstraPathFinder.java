package pathfinder;

import graph.Edge;
import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import java.util.*;

/**
 * This is a mutable sub class of graph which serves the purpose to find the
 * shortest path between two node in graph
 */
public class DijkstraPathFinder<N> extends Graph<N, Double> {

    /** Find the shortest path between two nodes
     *
     * @param startNode the start node of the path
     * @param endNode the destination of the path
     * @return return a path that links two nodes, null if no path found
     * @throws NullPointerException if {@code startNode} or {@code endNode} are
     *                              not in the graph
     *
     */
    public Path<N> findPath(N startNode, N endNode) {
        if (!containsNode(startNode) || !containsNode(endNode)) {
            throw new NullPointerException("unknown node");
        }
        Queue<Path<N>> active = new PriorityQueue<Path<N>>(new Comparator<Path<N>>() {
            @Override
            public int compare(Path<N> path1, Path<N> path2) {
                return Double.compare(path1.getCost(), path2.getCost());
            }
        });
        Path<N> startPath = new Path<N>(startNode);
        active.add(startPath);
        List<N> finished = new ArrayList<N>();
        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            if (minDest.equals(endNode)) {
                return minPath;
            }
            if (!finished.contains(minDest)) {
                for (Edge<Double, N> edge : listChildren(minDest)) {
                    if (!finished.contains(edge.getChild())) {
                        Path<N> newPath = minPath.extend(edge.getChild(), edge.getLabel());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
        return null;
    }
}
