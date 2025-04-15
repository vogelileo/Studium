import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

public class SortedLinkedListTest {


    @Test
    public void testIntegerLinkedList() {
        Integer[] array = new Integer[] {32, 53, 63, 112, 5, 552, 3, 6, 22};
        SortedLinkedList<Integer> linkedList = new SortedLinkedList<>(Integer::compareTo);
        linkedList.fillListFromArray(array);

        Arrays.sort(array);
        Assertions.assertTrue(linkedList.compareToArray(array));
    }

    @Test
    public void testStringLinkedList() {
        String[] array = new String[]{"dirt", "dad", "family", "president", "description", "organization"};
        SortedLinkedList<String> linkedList = new SortedLinkedList<>(String::compareTo);
        linkedList.fillListFromArray(array);

        Arrays.sort(array);
        Assertions.assertTrue(linkedList.compareToArray(array));
    }

    @Test
    public void testDateLinkedList() {
        Date[] array = new Date[]{new Date(414124124), new Date(414122314), new Date(453324124), new Date(414124555), new Date(414126425), new Date(414524555)};
        SortedLinkedList<Date> linkedList = new SortedLinkedList<>(Date::compareTo);
        linkedList.fillListFromArray(array);

        Arrays.sort(array);
        Assertions.assertTrue(linkedList.compareToArray(array));
    }
}
