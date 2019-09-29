package p2.sorts;

import java.util.Comparator;

import cse332.exceptions.NotYetImplementedException;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, 0, array.length - 1, comparator);
    }

    private static <E> void quickSort(E[] array, int start, int end, Comparator<E> comparator) {
        if (end - start < 1) {
            return;
        }
        E pivot = array[start];
        int i = start + 1;
        int j = end;
        while (i < j) {
            while (comparator.compare(array[i], pivot) < 0 && i < j) {
                i++;
            }
            while (comparator.compare(array[j], pivot) > 0 && i < j) {
                j--;
            }
            if (i != j) {
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        // i == j
        if (comparator.compare(pivot, array[i]) > 0) {
            // pivot greater than meeting point
            array[start] = array[i];
            array[i] = pivot;
        } else {
            array[start] = array[i - 1];
            array[i - 1] = pivot;
            i--;
        }
        quickSort(array, start, i - 1, comparator);
        quickSort(array, i + 1, end, comparator);
    }
}
