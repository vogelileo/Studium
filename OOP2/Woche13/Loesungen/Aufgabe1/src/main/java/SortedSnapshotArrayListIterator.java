import java.util.*;

public class SortedSnapshotArrayListIterator<T> implements Iterator<T> {

    private int currentIndex;
    private final T[] elements;

    public SortedSnapshotArrayListIterator(ArrayList<T> elements, Comparator<T> comparator) {
        var array = elements.toArray();
        Arrays.sort(array, comparator);
        this.elements = array;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < elements.length -1;
    }

    @Override
    public T next() {
        return elements[currentIndex++];
    }
}