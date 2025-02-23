import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionUtilsTest {

    @Test
    public void mergeAndSortEmployee() {
        var alice = new Employee("Alice", 3050);
        var charlie =  new Employee("Charlie", 3800);
        var bob = new Employee("Bob", 3006);
        var dave = new Employee("Dave", 3400);

        List<Employee> employees = List.of(alice, charlie);
        List<Employee> employees2 = List.of(bob,  dave);
        var combinedList =  CollectionUtils.mergeAndSort(employees, employees2);
        assertEquals(combinedList, List.of(bob, alice, dave, charlie));

    }

    @Test
    public void mergeAndSortNumbers() {
        List<Integer> list1 = List.of(1, 3, 5);
        List<Integer> list2 = List.of(2, 4, 6);
        var combinedList =  CollectionUtils.mergeAndSort(list2, list1);
        assertEquals(combinedList, List.of(1, 2, 3, 4, 5, 6));
    }

    @Test
    public void mergeAndSortStrings() {
        List<String> list1 = List.of("ZA", "AB", "HC");
        List<String> list2 = List.of("BA", "BC", "CD");
        var combinedList =  CollectionUtils.mergeAndSort(list2, list1);
        assertEquals(combinedList, List.of("AB", "BA", "BC", "CD", "HC", "ZA"));
    }

    @Test
    public void mergeAndSortChar() {
        List<Character> list1 = List.of('a', 'c', 'd');
        List<Character> list2 = List.of('b', 'z', 'f');
        var combinedList =  CollectionUtils.mergeAndSort(list2, list1);
        assertEquals(combinedList, List.of('a', 'b', 'c', 'd', 'f', 'z'));
    }
}