package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;
    private Comparator<E> comparator;
    
    public MinFourHeap(Comparator<E> comparator) {
    	capacity = 10;
		data = (E[]) new Object[capacity];
		size = 0;
		this.comparator = comparator;
    }

    @Override
    public boolean hasWork() {
        return size != 0;
    }

    @Override
    public void add(E work) {
    	if(size == data.length - 1) {
    		resize();
    	}
    	size++;
		int i = percolateUp(size - 1, work);
		data[i] = work;
    }
    
    public void resize() {
    	capacity = 2 * capacity;
        E[] newData = (E[])new Object[capacity];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
    
    public int percolateUp(int hole, E val) {
    	while(hole > 0 && comparator.compare(val, data[(hole - 1) / 4]) < 0) {
    		data[hole] = data[(hole - 1) / 4];
    		hole = (hole - 1) / 4;
    	}
    	return hole;
    }

    @Override
    public E peek() {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	}
        return data[0];
    }

    @Override
    public E next() {
    	if(size == 0) {
    		throw new NoSuchElementException();
    	}
    	E ans = data[0];
    	int hole = percolateDown(0, data[size - 1]);
    	data[hole] = data[size - 1];
    	size--;
    	return ans;
    }
    
    public int percolateDown(int hole, E val) {
    	while(4 * hole + 1 < size) { 		
	    	int i = 4 * hole + 1;
	    	int target = i;
	    	while (i < size && i < 4 * hole + 5) {
	    	    if (comparator.compare(data[target], data[i]) > 0) {
	    	        target = i;
	    	    }
	    	    i++;
	    	}
	    	if(comparator.compare(data[target], val) < 0) {
                data[hole] = data[target];
                hole = target;
            } else {
                break;
            } 	
    	}
    	return hole;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        capacity = 10;
        data = (E[])new Object[capacity];
    }
}
