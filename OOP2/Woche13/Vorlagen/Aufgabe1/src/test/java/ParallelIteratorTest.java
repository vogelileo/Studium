import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class ParallelIteratorTest {

    @Test
    public void testReverseIterator() {
        ArrayList<Integer> list = new ArrayList<>(Comparator.comparingInt(o -> o));
        list.add(43);
        list.add(72);
        list.add(87);
        list.add(19);

        Iterator<Integer> it = list.reverseIterator();
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(list.get(list.size() - i - 1), it.next());
        }
    }

    @Test
    public void testSnapshotIterator() {
        Integer[] array = new Integer[] {76, 34, 87, 12, 49, 12};
        Comparator<Integer> comparator = Comparator.comparingInt(o -> o);
        ArrayList<Integer> list = new ArrayList<>(comparator);
        list.addAll(array);
        Integer[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray, comparator);
        Iterator<Integer> it = list.sortedSnapshotIterator();

        for (int i = 0; i < array.length; i++) {
            Assertions.assertEquals(array[i], list.get(i));
            Assertions.assertEquals(sortedArray[i], it.next());
        }
    }

    @Test
    public void testParallelIterator() {
        Integer[] array = new Integer[] {76, 34, 87, 12, 49, 12};
        Comparator<Integer> comparator = Comparator.comparingInt(o -> o);
        ArrayList<Integer> list = new ArrayList<>(comparator);
        list.addAll(array);

        ParallelIterator<Integer, Integer> it = new ParallelIterator<>(list.iterator(), list.reverseIterator());

        int count = 0;
        while (it.hasNext()) {
            Pair<Integer, Integer> pair = it.next();
            Assertions.assertEquals(array[count], pair.getList1Element());
            Assertions.assertEquals(array[array.length - 1- count], pair.getList2Element());
            count++;
        }
    }

    @Test
    public void testParallelIteratorTwoLists() {
        Integer[] array = new Integer[] {76, 34, 87, 12, 49, 12};
        Comparator<Integer> comparator = Comparator.comparingInt(o -> o);
        ArrayList<Integer> list = new ArrayList<>(comparator);
        list.addAll(array);

        Integer[] secondArray = new Integer[] {32, 632, 74, 8, 11, 64, 91, 539, 41};
        ArrayList<Integer> list2 = new ArrayList<>(comparator);
        list2.addAll(secondArray);

        ParallelIterator<Integer, Integer> it = new ParallelIterator<>(list.iterator(), list2.iterator());

        int count = 0;
        while (it.hasNext()) {
            Pair<Integer, Integer> pair = it.next();
            Assertions.assertEquals(array[count], pair.getList1Element());
            Assertions.assertEquals(secondArray[count], pair.getList2Element());
            count++;
        }
    }
}
