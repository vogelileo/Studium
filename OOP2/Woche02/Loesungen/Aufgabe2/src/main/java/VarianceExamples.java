import java.util.*;

public class VarianceExamples {

    public static double sum(Collection<? extends Number> numbers) {
        double sum = 0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }

    public static void addNumbers(List<? super Integer> list, Collection<? extends Integer> source) {
        list.addAll(source);
    }

    public static <T extends Comparable<T>> T findMax(Collection<T> coll) {
        return coll.stream().max(Comparator.naturalOrder()).orElse(null);
    }

    public static <T> List<T> filterByType(Collection<?> source, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Object obj : source) {
            if (clazz.isInstance(obj)) {
                result.add(clazz.cast(obj));
            }
        }
        return result;
    }
}
