import java.util.ArrayList;
import java.util.List;

public class DynamicHashMap<K, V> {
    private final ArrayList<Bucket<K, V>> directories;
    private int globalDepth = 1;

    public DynamicHashMap(){
        this.directories = new ArrayList<>();
        for (int i = 0 ; i < Math.pow(2, globalDepth); i++){
            this.directories.add(new Bucket<>(globalDepth));
        }
    }

    private int getIndex(K key){
        var hash = key.hashCode();
        var binaryString = Integer.toBinaryString(hash);
        var index = binaryString.length() - globalDepth;
        var relevantPart = binaryString.substring(Math.max(index, 0));
        return Integer.parseInt(relevantPart,2);
    }

    public V put(K key, V value) {
        var index = this.getIndex(key);
        Bucket<K, V> bucket = this.directories.get(index);

        if (bucket.contains(key) || bucket.hasSpace()) {
            try {
                return bucket.put(key, value);
            } catch (Exception ignored){}
        } else {
            var items = this.directories.get(index).getValues();
            items.add(new Entry<>(key, value));
            if (bucket.getLocalDepth() == this.globalDepth){
                this.globalDepth++;
                bucket.increaseLocalDepth();
                this.expandDirectory(index, bucket.getLocalDepth());
                this.putAll(items);
            }
             else if (this.globalDepth > bucket.getLocalDepth()){
                bucket.increaseLocalDepth();
                this.directories.set(index, new Bucket<>(bucket.getLocalDepth()));
                this.putAll(items);
            }
        }
        return null;
    }

    private void expandDirectory(int index, int depth){
        var size = this.directories.size();
        this.directories.addAll(List.copyOf(this.directories));
        this.directories.set(index, new Bucket<>(depth));
        this.directories.set(index + size, new Bucket<>(depth));
    }

    private void putAll(ArrayList<Entry<K, V>> list) {
        for (Entry<K, V> entry : list){
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public V get(K key){
        var index = this.getIndex(key);
        Bucket<K, V> bucket = this.directories.get(index);
        if (bucket == null) return null;
        return bucket.getValue(key);
    }

    public V delete(K  key){
        var index = this.getIndex(key);
        Bucket<K, V> bucket = this.directories.get(index);
        return bucket.delete(key);
    }

}
