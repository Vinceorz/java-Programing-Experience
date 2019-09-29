package graph;

import java.util.Comparator;

/**
 * This class represents an immutable directed labeled graph
 *
 * Specification fields:
 *  @spec.specfield label : String // The label which contains the related information of the edge
 *  @spec.specfield childNode : String // The child node which is the destination of the edge
 */

public class Edge<E, T>  {
    // Abstraction Function:
    //  AF(Edge) = a edge between nodes, e, the label is e.getLabel(), the children node is
    //              e.getChild(), the label can be altered with e.setLabel(label)
    //              the child node can be altered with e.setChildNode(childNode)
    //
    // Representation Invariant:
    //  label != null and childrenNode != null

    /** The label of edge */
    private final E label;

    /** The child node that edge directs to */
    private final T childNode;

    /**
     * Return hash code of this edge.
     *
     * @return hash code of this edge
     */
    @Override
    public int hashCode() {
        return 31 * label.hashCode() + childNode.hashCode();
    }

    /**
     * Return true if this edge is the same as the other edge
     *
     * @param other edge that is given to compare with this edge
     * @return true if this edge is the same as the other edge
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Edge<?, ?>)) {
            return false;
        }
        Edge<?, ?> result  = (Edge<?, ?>) other;
        return label.equals(result.getLabel()) && childNode.equals(result.getChild());
    }

    /**
     * Constructs a new Edge with label and child node
     *
     * @param label the given label needed to put into edge
     * @param childNode the given child node needed to put into edge
     * @spec.requires {@code label != null && childNode != null}
     * @spec.modifies this.label, this.childNode
     * @spec.effects Constructs a new Edge with label and childNode
     */
    public Edge(E label, T childNode) {
        this.label = label;
        this.childNode = childNode;
        checkRep();
    }

    /** Return the label of this edge
     *
     * @return return the String representation of label
     */
    public E getLabel() {
        checkRep();
        return this.label;
    }

    /** Return the child node of this edge
     *
     * @return return the String representation of child node
     */
    public T getChild() {
        checkRep();
        return this.childNode;
    }

    /** Return the String representation of the edge which is its label
     *
     * @return return the toString representation of the edge
     */
    public String toString() {
        checkRep();
        return this.childNode + "(" + this.label + ")";
    }

    /** Throws an exception if the representation invariant is violated. */
    private void checkRep() {
        assert (this.label != null) : "label is not initialized";
        assert (this.childNode != null) : "child node is not initialized";
    }
}
