import java.util.Comparator;

public class AdaptablePriorityQueue<K, V> implements IAdaptablePriorityQueue<K, V> {

    private Entry<K, V> root;
    private final Comparator<K> comparator;
    private int count;

    public AdaptablePriorityQueue(Comparator<K> comparator) {
        this.comparator = comparator;
    }


    @Override
    public int size() {
        int counter = 0;
        Entry<K,V> head = root;
        while(head != null) {
            counter++;
            head = head.getNext();
        }
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Entry<K, V> min() throws EmptyPriorityQueueException {
        if(isEmpty()){
            throw new EmptyPriorityQueueException("queue empty");
        }

        return root;
    }

    @Override
    public void insert(K key, V value) {
        Entry<K,V> newEntry = new Entry<K,V>(key, value);
        if(isEmpty()){
            root = newEntry;
            return;
        }
        Entry<K,V> head = root;
        if(comparator.compare(head.getKey(), newEntry.getKey()) > 0){
            newEntry.setNext(head);
            root = newEntry;
            return;
        }

        while(head != null){
            if(head.getNext() == null) {
                head.setNext(newEntry);
                break;
            }

            if(comparator.compare(head.getKey(), newEntry.getKey()) < 0 || comparator.compare(head.getKey(), newEntry.getKey()) == 0) {
                head.setNext(newEntry);
                break;
            }
            head = head.getNext();
        }


    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
       //TODO
        if(isEmpty()){
            throw new EmptyPriorityQueueException("queue empty");
        }
        Entry<K,V> minEntry = root;
        root = minEntry.getNext();
        return minEntry;
    }

    @Override
    public void print() {
        //TODO Implement methods


    }

    @Override
    public Entry<K, V> remove(Entry<K, V> entry) {
        if(root == null){
            return null;
        }

        Entry<K,V> head = root;
        if(head.equals(entry)){
            return removeMin();
        }
        while(head != null){
            if(head.getNext() != null){
                Entry<K,V> nextHead = head.getNext();
                if(nextHead.equals(entry)){
                    Entry<K,V> nextHeadSon = nextHead.getNext();
                    head.setNext(nextHeadSon);
                    return nextHead;
                }

            }
            head = head.getNext();


        }
        return null;


    }

    @Override
    public boolean replaceValue(Entry<K, V> entry, V value) {
        if(root == null){
            return false;
        }
        Entry<K,V> head = root;
        while(head != null){
            if(head.equals(entry)){
                head.setValue(value);
                return true;
            }
            head = head.getNext();
        }
        return false;

    }

    @Override
    public boolean replaceKey(Entry<K, V> entry, K key) {
        boolean removeWorked = remove(entry) != null;
        if(removeWorked){
            insert(key, entry.getValue());
            return true;
        }
        return false;
    }
}
