import java.util.ArrayList;

public class Bucket<K, V> {
    private ArrayList<Entry<K, V>> array;
    private final int maxSize = 3;

    private int localDepth;

    public Bucket(int localDepth){
        this.array = new ArrayList<>();
        this.localDepth = localDepth;
    }

    public int getLocalDepth() {return localDepth;}

    public void increaseLocalDepth(){
        this.localDepth++;
    }

    private Entry<K, V> getEntry(K key){
        for (Entry<K, V> entry : array){
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null;
    }

    public V getValue(K key){
        var entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    public V put(K key, V value) throws Exception {
        if (this.contains(key)){
            var entry = this.getEntry(key);
            assert entry != null;
            var oldValue = entry.getValue();
            entry.setValue(value);
            return oldValue;
        }
        if (this.hasSpace()){
            this.array.add(new Entry<>(key, value));
            return null;
        }
       throw new Exception("Bucket has no Space");
    }

    public V delete(K key){
        var entry = getEntry(key);
        if (entry != null){
            this.array.remove(entry);
            return entry.getValue();
        }
       return null;
    }

    public boolean contains(K key){
        return this.getValue(key) != null;
    }

    public ArrayList<Entry<K, V>> getValues(){
        return (ArrayList<Entry<K, V>>) this.array.clone();
    }

    public boolean hasSpace(){
        return this.array.size() < this.maxSize;
    }

}
