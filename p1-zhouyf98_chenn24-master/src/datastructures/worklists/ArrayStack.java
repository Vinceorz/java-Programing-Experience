package datastructures.worklists;

import java.util.NoSuchElementException;
import cse332.interfaces.worklists.LIFOWorkList;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] stack;
    private int size;
    private int maxSize;

    public ArrayStack() {
        maxSize = 10;
        stack = (E[])new Object[maxSize];
        size = 0;
    }

    // push the work given onto the top of the stack.
    // resize the array to double its current size if full.
    @Override
    public void add(E work) {
        if (size < maxSize) {
            stack[size] = work;
        } else {
            maxSize = 2 * maxSize;
            E[] newStack = (E[])new Object[maxSize];
            for (int i = 0; i < stack.length; i++) {
                newStack[i] = stack[i];
            }
            newStack[size] = work;
            stack = newStack;
        }
        size++;
    }

    // return the item at the top of the stack.
    // throw NoSuchElementException if stack is empty.
    @Override
    public E peek() {
    	if (size == 0) {
    		throw new NoSuchElementException();
    	}
        return stack[size - 1];
    }

    // remove and return the item at the top of the stack.
    // throw NoSuchElementException if stack is empty.
    @Override
    public E next() {
    	if (size == 0) {
    		throw new NoSuchElementException();
    	}
        E result = stack[size - 1];
        size--;
        return result;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
    }
}
