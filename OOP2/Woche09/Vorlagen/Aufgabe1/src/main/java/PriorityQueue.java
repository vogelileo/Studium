import java.util.Comparator;
import java.util.LinkedList;

public class PriorityQueue<K, V> implements
        IPriorityQueue<K, V> {

    private final LinkedList<Entry<K, V>> list;
    private final Comparator<K> comparator;

    public PriorityQueue(Comparator<K> comparator) {
        this.comparator = comparator;
        list = new LinkedList<>();
    }

    //TODO implement methods

    @Override
    public int size() {

        return list.size();

    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Entry<K, V> min() throws EmptyPriorityQueueException {
        if(isEmpty()){
            throw new EmptyPriorityQueueException("Queue empty");
        }
        Entry<K, V> min = null;
        for (Entry<K, V> entry: list){
            if(min == null || comparator.compare(entry.getKey(), min.getKey()) < 0){
                min = entry;
            }
        }
        return min;

    }

    @Override
    public void insert(K key, V value) {
        list.add(new Entry<K, V>(key, value));
    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
        if(isEmpty()){
            throw new EmptyPriorityQueueException("queue empty");
        }
        Entry<K,V> minItem = min();
        list.remove(minItem);
        return minItem;
    }

    @Override
    public void print() {
        for (Entry<K, V> entry: list){
            System.out.println(entry.getKey().toString() + entry.getValue()); // uses Entry.toString()

        }

    }
}
