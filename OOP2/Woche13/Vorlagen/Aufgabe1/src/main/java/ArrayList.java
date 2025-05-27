import java.util.Comparator;
import java.util.Iterator;

public class ArrayList<T> {

    private T[] elements;
    private final Comparator<T> comparator;

    public ArrayList(Comparator<T> comparator) {
        elements = (T[]) new Object[5];
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void add(T o) {
        if (getArraySize() - 1 <= size()) {
            Object[] tmp = new Object[(int) (getArraySize() * 1.5)];
            System.arraycopy(elements, 0, tmp, 0, size());
            elements = (T[]) tmp;
        }
        elements[size()] = o;
    }

    public Iterator<T> iterator() {
        return new ArrayListIterator<>(this);
    }

    public Iterator<T> sortedSnapshotIterator() {
        return new SortedSnapshotArrayListIterator<>(elements, size(), comparator);
    }

    public Iterator<T> reverseIterator() {
        return new ReverseArrayListIterator<>(this);
    }

    public int size() {
        int count = 0;
        for (T element : elements) {
            if (element == null) {
                return count;
            }
            count++;
        }
        return count;
    }

    public void addAll(T[] values) {
        for (T value : values) {
            add(value);
        }
    }

    public T get(int index) {
        if (index < 0 && index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        return elements[index];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            builder.append(elements[i].toString());
            builder.append("\n");
        }

        return builder.toString();
    }

    public int getArraySize() {
        return elements.length;
    }

    public static void main(String[] args) {

    }
}

