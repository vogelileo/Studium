import java.util.Arrays;
import java.util.List;

public class BinarySearch {

    public static <E extends Comparable<E>> int searchBinaryRecursive(List<? extends E> sortedList, int start, int end, E searchElement) {
        int pivot = start + ((end - start) / 2);
        if (sortedList.size() == 0) {
            System.out.println("Array leer.");
            return -1;
        }
        if (pivot >= sortedList.size()) {
            System.out.println(searchElement + " nicht im Array enthalten.");
            return -1;
        }
        int compareResult = searchElement.compareTo(sortedList.get(pivot));
        if (compareResult > 0) {
            return searchBinaryRecursive(sortedList, pivot + 1, end, searchElement);
        } else if (compareResult < 0 && start != pivot) {
            return searchBinaryRecursive(sortedList, start, pivot - 1, searchElement);
        } else if (compareResult == 0) {
            System.out.println(searchElement + " an Position " + pivot + " enthalten.");
            return pivot;
        } else {
            System.out.println(searchElement + " nicht im Array enthalten.");
            return -1;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 4, 6, 7, 21, 42, 52, 63, 74};
        System.out.println(searchBinaryRecursive(Arrays.asList(a), 0, a.length, 42));
    }
}
