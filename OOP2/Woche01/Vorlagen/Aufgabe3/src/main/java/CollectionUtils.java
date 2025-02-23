import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public static <T extends Comparable<T>> List<T> mergeAndSort(List<T> firstList, List<T> secondList){
        List<T> outputList = new ArrayList<>();
        outputList.addAll(firstList);
        outputList.addAll(secondList);

        outputList.sort(Comparable::compareTo);
        return outputList;
    }
    // TODO: Implement Method - mergeAndSort
}
