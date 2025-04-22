import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public class PriorityQueue<K, V> implements
        IPriorityQueue<K, V> {

    private final LinkedList<Entry<K, V>> list;
    private final Comparator<K> comparator;

    public PriorityQueue(Comparator<K> comparator) {
        this.comparator = comparator;
        list = new LinkedList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void insert(K key, V value) {
        ListIterator<Entry<K, V>> it = list.listIterator();
        while (it.hasNext()) {
            if (comparator.compare(key, it.next().getKey()) < 0) {
                it.previous();
                break;
            }
        }
        it.add(new Entry<>(key, value));
    }

    @Override
    public Entry<K, V> min() throws EmptyPriorityQueueException {
        if (list.isEmpty()) {
            throw new EmptyPriorityQueueException("The priority queue is empty");
        }
        return list.getFirst();
    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
        Entry<K, V> entry = min();
        list.removeFirst();
        return entry;
    }

    @Override
    public void print() {
        System.out.println("Print priority queue entries: ");
        int i = 0;
        for (Entry<K, V> entry : list) {
            System.out.println("Element " + (++i) + " with key '" + entry.getKey()
                    + "' has the value '" + entry.getValue() + "'");
        }
    }

    public static void main(String[] args) {

        PriorityQueue<Integer, String> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o));

        pq.insert(8, "eight");
        pq.insert(2, "two 1");
        pq.insert(5, "five");
        pq.insert(1, "one 1");
        pq.insert(2, "two 2");
        pq.insert(1, "one 2");
        pq.insert(4, "four 1");
        pq.insert(4, "four 2");
        pq.insert(7, "seven");
        pq.insert(2, "two 3");
        pq.insert(6, "six");
        pq.insert(3, "three");
        pq.insert(1, "one 3");
        pq.print();

    }
}
