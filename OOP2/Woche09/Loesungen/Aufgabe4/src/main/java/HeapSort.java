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

    private static <T> int get_largest_node(T[] array, int n, int parent, Comparator<T> comparator) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;

        int largest = parent;
        if (left < n && comparator.compare(array[left], array[largest]) < 0) {
            largest = left;
        }
        if (right < n && comparator.compare(array[right], array[largest]) < 0) {
            largest = right;
        }

        return largest;
    }

    static <T> void heapify(T[] arr, Comparator<T> comparator) {
        int n = arr.length;

        // Start from the last non-leaf node and go up to the root
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, i, comparator);
        }
    }

    public static <T> void heapify(T[] array, int parent, Comparator<T> comparator) {
        int largest = get_largest_node(array, array.length, parent, comparator);
        if (largest != parent) {
            // swap
            T temp = array[parent];
            array[parent] = array[largest];
            array[largest] = temp;
            // heapify swapped subtree
            heapify(array, largest, comparator);
        }

    }

    static <T> void percolate(T[] array, int parent, int n, Comparator<T> comparator) {
        int largest = get_largest_node(array, n, parent, comparator);

        if (largest == parent) {
            return;
        }

        T temp = array[parent];
        array[parent] = array[largest];
        array[largest] = temp;
        percolate(array, largest, n, comparator);
    }

    static <T> void sort(T[] arr, Comparator<T> comparator) {
        int n = arr.length;

        heapify(arr, comparator.reversed());

        while (n > 1) {
            // Swap first and last places
            T temp = arr[0];
            arr[0] = arr[n - 1];
            arr[n - 1] = temp;

            // decrease size of the heap
            n = n - 1;

            percolate(arr, 0, n, comparator.reversed());
        }

    }
}
