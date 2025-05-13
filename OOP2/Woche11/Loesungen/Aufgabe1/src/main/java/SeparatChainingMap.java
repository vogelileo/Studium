import java.util.*;

public class SeparatChainingMap<K, V> {
    private List<Entry<K, V>> entries;
    private int maxSize;
    private int size;

    public SeparatChainingMap() {
        maxSize = 10;
        this.entries = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            entries.add(null);
        }
    }

    private int getListIndex(K key) {
        return Math.abs(Objects.hashCode(key) % maxSize);
    }

    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    private Entry<K, V> getEntry(K key) {
        int index = getListIndex(key);
        Entry<K, V> current = entries.get(index);
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current;
            }
            current = current.getNext();
        }

        return null;
    }

    public V put(K key, V value) {
        int index = getListIndex(key);
        Entry<K, V> newEntry = new Entry<>(key, value);
        Entry<K, V> current = entries.get(index);
        if (current != null) {
            while (current != null) {
                if (current.getKey().equals(key)) {
                    V before = current.getValue();
                    current.setValue(value);
                    expandTable();
                    return before;
                }
                current = current.getNext();
            }
            current = entries.get(index);
            newEntry.setNext(current);
        }
        entries.set(index, newEntry);
        size++;
        expandTable();
        return null;
    }

    private void expandTable() {
        if (1.0d * size / maxSize > 0.7) {
            List<Entry<K, V>> tmp = entries;
            entries = new ArrayList<>();
            maxSize *= 2;
            for (int i = 0; i < maxSize; i++) {
                entries.add(null);
            }
            size = 0;
            for (Entry<K, V> entry : tmp) {
                while (entry != null) {
                    put(entry.getKey(), entry.getValue());
                    entry = entry.getNext();
                }
            }
        }
    }

    public V remove(K key) {
        int index = getListIndex(key);
        Entry<K, V> current = entries.get(index);
        if (current == null) {
            return null;
        }
        if (current.getKey().equals(key)) {
            V val = current.getValue();
            current = current.getNext();
            entries.set(index, current);
            size--;
            return val;
        } else {
            Entry<K, V> prev = null;
            while (current != null) {

                if (current.getKey().equals(key)) {
                    prev.setNext(current.getNext());
                    size--;
                    return current.getValue();
                }
                prev = current;
                current = current.getNext();
            }
            return null;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Entry<K, V> entry : entries) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    set.add(current.getKey());
                    current = current.getNext();
                }
            }
        }
        return set;
    }

    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        for (Entry<K, V> entry : entries) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    list.add(current.getValue());
                    current = current.getNext();
                }
            }
        }
        return list;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> list = new HashSet<>();
        for (Entry<K, V> entry : entries) {
            if (entry != null) {
                Entry<K, V> current = entry;
                while (current != null) {
                    list.add(current);
                    current = current.getNext();
                }
            }
        }
        return list;
    }

    public void print() {
        for (Entry<K, V> entry : entries) {
            if (entry == null) {
                System.out.println("null");
                continue;
            }
            while (entry != null) {
                System.out.print(entry);
                System.out.print(" --> ");
                entry = entry.getNext();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SeparatChainingMap<Integer, String> map = new SeparatChainingMap<>();
        map.put(6251, "Never");
        map.put(533, "Gonna");
        map.put(421, "Give");
        map.put(756, "You");
        map.put(1923, "Up");
        map.put(53, "Never");
        map.put(339, "Gonna");
        map.put(349, "Let");
        map.put(0, "You");
        map.put(2, "dads");
        map.put(42, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        map.print();
        System.out.println(map.size());

    }


}