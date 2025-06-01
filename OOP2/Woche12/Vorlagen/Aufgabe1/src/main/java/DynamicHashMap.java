import java.util.ArrayList;

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

        if(bucket.contains(key)){
            try {
                return bucket.put(key, value);
            } catch (Exception e) {
                return null;
            }

        }

        if(bucket.hasSpace()){
            try {
                return bucket.put(key, value);
            } catch (Exception e) {
                return null;
            }
        }

        if(bucket.getLocalDepth() == globalDepth){
            int size = directories.size();
            for(int i = 0; i< size; i++){
                directories.add(directories.get(i));
            }
            globalDepth++;
        }

        //Split Bucket process
        Bucket<K,V> newBucket = new Bucket<>(bucket.getLocalDepth() +1);
        bucket.increaseLocalDepth();

        ArrayList<Entry<K,V>> oldEntries = bucket.getValues();
        bucket = new Bucket<>(bucket.getLocalDepth());

        for (int i = 0; i < directories.size(); i++) {
            int mask = (1 << bucket.getLocalDepth()) - 1;
            if ((i & mask) == (index & mask)) {
                if ((i >> (bucket.getLocalDepth() - 1) & 1) == 0) {
                    directories.set(i, bucket);
                } else {
                    directories.set(i, newBucket);
                }
            }
        }

        for(Entry<K,V> entry: oldEntries){
            this.put(entry.getKey(), entry.getValue());
        }

        return this.put(key, value);
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
