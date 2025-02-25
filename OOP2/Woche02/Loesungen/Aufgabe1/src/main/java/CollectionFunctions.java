import java.util.*;

public class CollectionFunctions {
	public static <T> List<T> mergeToList(Collection<? extends T> coll1, Collection<? extends T> coll2) {
		var result = new ArrayList<T>();
		merge(coll1, coll2, result);
		return result;
	}
	
	public static <T> Set<T> mergeToSet(Collection<? extends T> coll1, Collection<? extends T> coll2) {
		var result = new HashSet<T>();
		merge(coll1, coll2, result);
		return result;
	}
	
	public static <T> void merge(Collection<? extends T> inputColl1, Collection<? extends T> inputColl2, Collection<? super T> targetColl) {
		targetColl.addAll(inputColl1);
		targetColl.addAll(inputColl2);
	}
	
	public static <T> Set<T> findDuplicates(Collection<T> coll) {
		var set = new HashSet<T>(coll);
		var list = new ArrayList<T>(coll);
		
		
		for (T item : set) {
			list.remove(item);
		}
		return new HashSet<>(list);
	}
	
	public static <T> T mostFrequent(Collection<T> coll) {
		var map = new HashMap<T, Integer>();
		int maxCount = 0;
		T result = null;
		for (T item : coll) {
			if (!map.containsKey(item)) {
				map.put(item, 0);
			}
			var count = map.get(item) + 1;
			map.put(item, count);
			if (count > maxCount) {
				maxCount = count;
				result = item;
			}
		}
		return result;
	}
}
