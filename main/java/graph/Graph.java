package graph;

import java.util.*;


/**
 * This class represents an mutable directed labeled adjacency
 *
 * Specification fields:
 *  @spec.specfield adjacency : String // The adjacency which contains nodes and edges between them,
 *                                     where nodes and edges between nodes are unique
 */
public class Graph<N, E> {
    /** The directed labeled adjacency */
    private Map<N, List<Edge<E, N>>> adjacency;

    /** The debug that take charge of the check rep process */
    private final static boolean debug = false;

    // Abstraction Function:
    //  AF(adjacency) = a adjacency, g, with nodes = g.keySet, with edge = g.get(node),
    //              g is empty when adjacency.size == 0
    //              if a node does not nave any edge, adjacency.get(node).size == 0
    //              the children nodes (destination) of a node are adjacency.listChildren(node)
    //              the label of the edge is edge.getLabel, where g is
    //              [node[childNode(edge)], ...] ...]
    //
    // Representation Invariant:
    //  adjacency != null if adjacency != null, every node inside adjacency != null

    /** @spec.effects Constructs a new Graph */
    public Graph() {
        adjacency = new HashMap<N, List<Edge<E, N>>>();
        checkRep();
    }

    /** Add a node to the Graph
     *
     * @param node the single node which is needed to add to the Graph
     * @spec.requires {@code node != null}
     * @spec.modifies this.adjacency
     * @spec.effects add a node to the adjacency as a key if it is not included in the adjacency already
     * @return return true if the node is successfully added to the adjacency otherwise false
     *
     */
    public boolean addNode(N node) {
        checkRep();
        if (!adjacency.containsKey(node)) {
            this.adjacency.put(node, new ArrayList<Edge<E, N>>());
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }

    /** Add a given edge with label to the given node
     *
     * @param node the single node which is needed to add a edge
     * @param label the label of the edge
     * @param childNode the child node of the given node
     * @spec.requires {@code node != null && label != null && childNode != null}
     * @spec.modifies this.adjacency
     * @spec.effects add an edge with given label and child node to a node in the adjacency
     * @return return true if the edge is successfully added to the node otherwise false
     *
     */
    public boolean addEdge(N node, E label, N childNode) {
        checkRep();
        if (this.adjacency.containsKey(node) && this.adjacency.containsKey(childNode)) {
            List<Edge<E, N>> edges = this.adjacency.get(node);
            Edge<E, N> newEdge = new Edge<E, N>(label, childNode);
            if (!edges.contains(newEdge)) {
                edges.add(newEdge);
                checkRep();
                return true;
            }
        }
        checkRep();
        return false;
    }

    /** Returns a set of existing nodes in the Graph
     *
     * @return return an unmodified set of nodes in the adjacency
     */
    public Set<N> listNodes() {
        checkRep();
        Set<N> result = new TreeSet<N>(this.adjacency.keySet());

        return result;
    }

    /** Returns the difference in area between this and s.
     *
     * @return return true iff it is empty and false if it isn't
     */
    public boolean isEmpty() {
        checkRep();
        return adjacency.isEmpty();
    }

    /** Returns a list of children node of the given parent node
     *
     * @param node the single node which is the parent node
     * @spec.requires {@code node != null}
     * @return an new list of children equals to the set of children nodes of the given node
     */
    public List<Edge<E, N>> listChildren(N node) {
        checkRep();
        List<Edge<E, N>> edges = this.adjacency.get(node);
        List<Edge<E, N>> result = new ArrayList<Edge<E, N>>(edges);
        return result;
    }

    /** Returns if the adjacency contains a node
     *
     * @param node the single node which is needed to check if inside the adjacency
     * @spec.requires {@code node != null}
     * @return return true iff the adjacency contains the node
     */
    public boolean containsNode(N node) {
        checkRep();
        return this.adjacency.containsKey(node);
    }

    /** Remove a node from the Graph
     *
     * @param node the single node which is needed to remove from the Graph
     * @spec.requires {@code node != null}
     * @spec.modifies this.adjacency
     * @spec.effects remove a node from this.adjacency if the adjacency contains the given node
     * @return return true if the node is successfully removed from the adjacency
     *
     */
    public boolean removeNode(N node) {
        checkRep();
        if (this.adjacency.containsKey(node)) {
            this.adjacency.remove(node);
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }

    /** Remove the edges from the adjacency
     *
     * @param label the given label to locate which edge to be removed
     * @param node the given node to locate which edge to be removed
     * @param childNode the given child node to locate which edge to be removed
     * @spec.requires {@code node != null && label != null}
     * @spec.modifies this.adjacency
     * @spec.effects remove an edge with the given label in this.adjacency
     * @return return true if the edge is successfully to be removed from the adjacency
     *
     */
    public boolean removeEdges(N node, E label, N childNode){
        checkRep();
        if(this.adjacency.containsKey(node) && this.adjacency.containsKey(childNode)) {
            List<Edge<E, N>> edges = this.adjacency.get(node);
            Edge<E, N> targetEdge = new Edge<E, N>(label, childNode);
            if(edges.contains(targetEdge)) {
                edges.remove(targetEdge);
                checkRep();
                return true;
            }
        }
        checkRep();
        return false;
    }

    /** Clear everything in the Graph (including both nodes and edges)
     *
     * @spec.modifies this.adjacency
     * @spec.effects clear the nodes in this.adjacency
     */
    public void clearGraph() {
        if (debug) {
            checkRep();
        }
        this.adjacency.clear();
        if (debug) {
            checkRep();
        }
    }

    /** Return the size of the Graph
     *
     * @return return the size (number of nodes) of the adjacency
     */
    public int size() {
        if (debug) {
            checkRep();
        }
        return this.adjacency.size();
    }

    /** Throws an exception if the representation invariant is violated. */
    private void checkRep() {
        if(debug) {
            assert (adjacency != null) : "Graph doesn't exist";
            if (adjacency.size() != 0) {
                Set<N> nodes = adjacency.keySet();
                for(N node : nodes) {
                    assert (node != null) : "adjacency contains node that doesn't exist";
                }
            }
        }
    }
}
