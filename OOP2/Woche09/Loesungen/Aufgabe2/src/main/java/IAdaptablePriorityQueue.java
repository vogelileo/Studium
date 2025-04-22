public interface IAdaptablePriorityQueue<K, V> {

  int size();

  boolean isEmpty();

  Entry<K, V> min() throws EmptyPriorityQueueException;

  void insert(K key, V value);

  Entry<K, V> removeMin() throws EmptyPriorityQueueException;

  void print();

  Entry<K, V> remove(Entry<K, V> entry);

  boolean replaceValue(Entry<K, V> entry, V value);

  boolean replaceKey(Entry<K, V> entry, K key);
}
 
 
 
 
