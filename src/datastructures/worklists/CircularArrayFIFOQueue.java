package datastructures.worklists;

import java.util.NoSuchElementException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] queue;
    private int size;
    private int front;
    private int rear;
    private final int capacity;

    // initialize an instance of a fixed capacity.
    //
    // capacity should be a positive int passed in as argument,
    // throw IllegalArgumentException otherwise.
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.capacity = capacity;
        queue = (E[])new Comparable[capacity];
        size = 0;
        front = -1;
        rear = -1;
    }

    // add the give work to the front of the queue.
    //
    // throw IllegalStateException if queue is full.
    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException();
        } else {
            if (front == -1) {
                front = 0;
            }
            size++;
            rear = (rear + 1) % capacity;
            queue[rear] = work;
        }

    }

    // return item at the front of the queue.
    @Override
    public E peek() {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	}
        return queue[front];
    }

    // return item at an specified index.
    //
    // integer passed in should be between 0 and size-1,
    // throw IndexOutOfBoundsException otherwise.
    //
    // throw NoSuchElementException if queue is empty.
    @Override
    public E peek(int i) {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	}
    	if (i < 0 || i >= size) {
    		throw new IndexOutOfBoundsException();
    	}
        return queue[(front + i) % capacity];
    }

    // remove and return the item at the front.
    //
    // throw NoSuchElementException if queue is empty.
    @Override
    public E next() {
    	if (size == 0) {
    		throw new NoSuchElementException();
    	}
        size--;
        E result = queue[front];
        front = (front + 1) % capacity;
        return result;
    }

    // replace an item at a given index with a value given.
    //
    // throw NoSuchElementException if queue is empty.
    //
    // throw IndexOutOfBoundsException if index is invalid.
    @Override
    public void update(int i, E value) {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	}
    	if (i < 0 || i >= size) {
    		throw new IndexOutOfBoundsException();
    	}
        queue[(front + i) % capacity] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        queue = (E[])new Comparable[capacity];
        size = 0;
        front = -1;
        rear = -1;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        java.util.Iterator<E> this_it = this.iterator();
        java.util.Iterator<E> other_it = other.iterator();
        while (this_it.hasNext() && other_it.hasNext()) {
            E this_element = this_it.next();
            E other_element = other_it.next();
            if (this_element.compareTo(other_element) != 0) {
                return this_element.compareTo(other_element);
            }
        }
        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            return this.compareTo(other) == 0;
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        java.util.Iterator<E> this_it = this.iterator();
        int result = 0;
        int i = 0;
        while (this_it.hasNext()) {
            E this_element = this_it.next();
            result += this_element.hashCode() * 13 * i;
            i++;
        }
        return result;
    }
}
