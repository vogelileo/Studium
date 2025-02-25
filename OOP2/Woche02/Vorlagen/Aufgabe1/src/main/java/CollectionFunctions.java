import java.util.*;

public class CollectionFunctions {
	// TODO: Implement
    public static<T> List<T> mergeToList(Collection<? extends T> col1, Collection<?extends T> col2){
        var newList = new ArrayList<T>();
        newList.addAll(col1);
        newList.addAll(col2);

        return newList;

    }

    public static<T> Set<T>  mergeToSet(Collection<? extends T> set1, Collection<? extends T> set2){
        Set<T> newSet = new HashSet<>();
        newSet.addAll(set1);
        newSet.addAll(set2);

        return newSet;
    }

    public static<T> void merge(Collection<? extends T> col1, Collection<? extends T> col2, Collection<? super  T> target){
        target.addAll(col1);
        target.addAll(col2);

    }

    public static<T> Set<T> findDuplicates(Collection<T> col1){
        var set = new HashSet<T>(col1);
        var list = new ArrayList<T>(col1);


        for (T item : set) {
            list.remove(item);
        }
        return new HashSet<>(list);
    }

    public static<T> T mostFrequent(Collection< T> col1){
        Map<T, Integer> map = new HashMap<T, Integer>();

        T currentMaxItem = null;


        for(T item: col1){
            if(map.containsKey(item)){
                map.put(item,map.get(item)+1);
            }else{
                map.put(item, 1);
            }


            if(currentMaxItem == null || map.get(currentMaxItem) < map.get(item)){
                currentMaxItem = item;
            }
        }

        return currentMaxItem;
    }
}
