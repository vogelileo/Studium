import java.util.Comparator;

public class AdaptablePriorityQueue<K, V> implements IAdaptablePriorityQueue<K, V> {

    private Entry<K, V> root;
    private final Comparator<K> comparator;
    private int count;

    public AdaptablePriorityQueue(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void insert(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        sortNewNode(entry);
        count++;
    }

    private void sortNewNode(Entry<K, V> entry) {
        Entry<K, V> previous = root;
        if (root == null || comparator.compare(root.getKey(), entry.getKey()) > 0) {
            previous = root;
            root = entry;
            root.setNext(previous);
        } else {
            Entry<K, V> currentEntry = root;
            while (comparator.compare(currentEntry.getKey(), entry.getKey()) <= 0 && currentEntry.getNext() != null) {
                previous = currentEntry;
                currentEntry = currentEntry.getNext();
            }
            if (currentEntry.getNext() == null && comparator.compare(currentEntry.getKey(), entry.getKey()) <= 0) {
                currentEntry.setNext(entry);
            } else {
                previous.setNext(entry);
                entry.setNext(currentEntry);
            }
        }
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public Entry<K, V> min() throws EmptyPriorityQueueException {
        if (root == null) {
            throw new EmptyPriorityQueueException("Adaptable Priority Queue is empty");
        }
        return root;
    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
        if (root == null) {
            throw new EmptyPriorityQueueException("Adaptable Priority Queue is empty");
        }
        Entry<K, V> currentMin = root;
        root = root.getNext();
        return currentMin;
    }

    @Override
    public void print() {
        Entry<K, V> current = root;
        while (current != null) {
            System.out.println(current.getKey());
            current = current.getNext();
        }
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> entry) {
        Entry<K, V> current = root;
        Entry<K, V> previous = null;

        if (current != null && root.equals(entry)) {
            root = root.getNext();
            return current;
        }

        while (current != null && !current.equals(entry)) {
            previous = current;
            current = current.getNext();
        }

        if (current == null) {
            return null;
        }

        previous.setNext(current.getNext());
        return current;
    }

    @Override
    public boolean replaceValue(Entry<K, V> entry, V value) {
        Entry<K, V> current = root;
        while (current != null && !current.equals(entry)) {
            current = current.getNext();
        }
        if (current == null) {
            return false;
        }
        current.setValue(value);
        return true;
    }

    @Override
    public boolean replaceKey(Entry<K, V> entry, K key) {
        Entry<K, V> replacedEntry = remove(entry);
        if (replacedEntry == null) {
            return false;
        }
        replacedEntry.setKey(key);
        replacedEntry.setNext(null);
        sortNewNode(replacedEntry);
        return true;
    }

    public static void main(String[] args) {
        AdaptablePriorityQueue<Integer, Integer> apq = new AdaptablePriorityQueue<>(Comparator.comparingInt(o -> o));
        apq.insert(1, 2);
        apq.insert(6, 2);
        apq.insert(3, 2);
        apq.insert(9, 2);
        apq.insert(2, 2);
        apq.insert(3, 2);

        apq.print();
        apq.replaceKey(new Entry<>(3, 2), 11);
        apq.print();
    }

}
