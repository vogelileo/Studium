import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BinarySearch {

    public static <E extends Comparable<E>> int searchBinaryRecursive(List<? extends E> sortedList, int start, int end, E searchElement) {
        if(sortedList.isEmpty()){
            return -1;
        }
        int midIndex = (end  - start) /2 + start;

        E e = sortedList.get(midIndex );
        int result = e.compareTo(searchElement);
        if(result == 0){
            return midIndex;
        } else if (start == end) {
            return -1;
        } else if(result >0){
            return searchBinaryRecursive(sortedList,start,midIndex -1,searchElement);
        }else { //result > 0
           return  searchBinaryRecursive(sortedList, midIndex + 1, end, searchElement);
        }

    }

    public static void main(String[] args) {
        Integer[] a = {1, 4, 6, 7, 21, 42, 52, 63, 74};

        List<String> stringList = new ArrayList<>(Arrays.asList(
                "dirt",
                "family",
                "description",
                "dealer",
                "safety"
        ));

        stringList.sort(Comparator.comparing(String::toString));
       System.out.println(searchBinaryRecursive(stringList, 0, stringList.size(), "Not Found"));

        System.out.println(searchBinaryRecursive(Arrays.asList(a), 0, a.length, 42));
        System.out.println(searchBinaryRecursive(Arrays.asList(a), 0, a.length, 43));
        System.out.println(searchBinaryRecursive(Arrays.asList(a), 0, a.length, 7));

    }
}
