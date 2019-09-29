package datastructures.worklists;

import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private Node<E, Node> head;
    private Node<E, Node> tail;
    private int size;

    public ListFIFOQueue() {
        head = null;
        tail = null;
        size = 0;
    }


    // add given work into the end of the queue.
    @Override
    public void add(E work) {
    	if (head == null) {
    		head = new Node<E, Node>(work);
    		tail = head;
    	} else {
    		tail.next = new Node<E, Node>(work);
            tail = tail.next;
    	}
        size++;
    }


    // return the next thing in the front of the queue.
    // throw NoSuchElementException if nothing is in the queue.
    @Override
    public E peek() {
    	if (size == 0) {
    		throw new NoSuchElementException();
    	}
        return head.data;
    }

    // remove and return the next thing in the front of the queue.
    @Override
    public E next() {
    	if (size == 0) {
    		throw new NoSuchElementException();
    	}
        size--;
        E result = head.data;
        head = head.next;
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    // clear content of the queue
    @Override
    public void clear() {
        size = 0;
        head = null;
        tail = null;	
    }

    private class Node<E, ListNode> {
        public E data;       // data stored in this node
        public Node next;

        // post: constructs a node with data 0 and null link
        public Node() {
            this(null, null);
        }

        // post: constructs a node with given data and null link
        public Node(E data) {
            this(data, null);
        }

        // post: constructs a node with given data and given link
        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
