public interface IPriorityQueue<K, V> {

  int size();

  boolean isEmpty();

  Entry<K, V> min() throws EmptyPriorityQueueException;

  void insert(K key, V value);

  Entry<K, V> removeMin() throws EmptyPriorityQueueException;

  void print();
}
 
 
 
 
