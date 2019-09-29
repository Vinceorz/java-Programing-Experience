package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        PriorityWorkList<E> heap = new MinFourHeap<>(comparator);
        for (E curr : array) {
            heap.add(curr);
            if (heap.size() > k) {
                // throw the smallest
                heap.next();
            }
        }
        // smallest n - k elements have been thrown away, what's left is the 
        // k largest
        for (int i = 0; i < k; i++) {
            array[i] = heap.next();
        }
        for (int i = k; i < array.length; i++) {
            array[i] = null;
        }
    }
}
