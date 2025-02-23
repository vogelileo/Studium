import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {

    public static <T extends Comparable<T>> List<T> mergeAndSort(Collection<T> c1, Collection<T> c2) {
        List<T> result = new ArrayList<>(c1);
        result.addAll(c2);
        result.sort(Comparable::compareTo);
        return result;
    }
}
