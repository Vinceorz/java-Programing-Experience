package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;
import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        PriorityWorkList<E> heap = new MinFourHeap<E>(comparator);
        for (E item : array) {
            heap.add(item);
        }
        int index = 0;
        while (heap.hasWork()) {
            array[index] = heap.next();
            index++;
        }
    }
}
