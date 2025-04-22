import java.util.Arrays;
import java.util.Comparator;

public class HeapSort {

    public static void main(String[] args) {
        Integer[] arr = {9, 4, 2, 1, 5, 7, 8, 3, 6};

        System.out.println("Original array:");
        System.out.println(Arrays.toString(arr));

        heapify(arr, Comparator.naturalOrder());

        System.out.println("Heapified array:");
        System.out.println(Arrays.toString(arr)); // expected: [1, 3, 2, 4, 5, 7, 8, 9, 6] or another valid min-heap

        sort(arr, Comparator.naturalOrder());

        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(arr)); // expected: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    static <T> void heapify(T[] arr, Comparator<T> comparator) {
        //TODO: implement
    }

    static <T> void sort(T[] arr, Comparator<T> comparator) {
        //TODO: implement
    }
}
