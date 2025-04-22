import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {
    @Test
    void testMaxHeapify() {
        Integer[] input = {4, 10, 3, 5, 1, 3};
        HeapSort.heapify(input, Comparator.reverseOrder());

        // In a max-heap, the root should be the largest
        assertEquals(10, input[0]);

        // Check if heap property holds for all parents
        for (int i = 0; i < input.length / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < input.length) {
                assertTrue(input[i] >= input[left]);
            }
            if (right < input.length) {
                assertTrue(input[i] >= input[right]);
            }
        }
    }

    @Test
    void testMinHeapify() {
        Integer[] input = {4, 10, 3, 5, 1, 3};
        HeapSort.heapify(input, Comparator.naturalOrder());

        // Root should be the smallest in a min-heap
        assertEquals(1, input[0]);

        for (int i = 0; i < input.length / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < input.length) {
                assertTrue(input[i] <= input[left]);
            }
            if (right < input.length) {
                assertTrue(input[i] <= input[right]);
            }
        }
    }

    @Test
    void testCharMaxHeapify() {
        Character[] input = {'a', 'b', 'c', 'd', 'e', 'f'};
        HeapSort.heapify(input, Comparator.reverseOrder());
        assertEquals('f', input[0]);

        for (int i = 0; i < input.length / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < input.length) {
                assertTrue(input[i] >= input[left]);
            }
            if (right < input.length) {
                assertTrue(input[i] >= input[right]);
            }
        }
    }

    @Test
    void testCHARMinHeapify() {
        Character[] input = {'a', 'c', 'd', 'b', 'e', 'f'};
        HeapSort.heapify(input, Comparator.naturalOrder());
        assertEquals('a', input[0]);

        for (int i = 0; i < input.length / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < input.length) {
                assertTrue(input[i] <= input[left]);
            }
            if (right < input.length) {
                assertTrue(input[i] <= input[right]);
            }
        }
    }

    @Test
    void testEmptyArray() {
        Integer[] empty = {};
        HeapSort.heapify(empty, Comparator.naturalOrder());
        assertEquals(0, empty.length);
    }

    @Test
    void testSingleElementArray() {
        Integer[] single = {42};
        HeapSort.heapify(single, Comparator.naturalOrder());
        assertEquals(42, single[0]);
    }

    @Test
    void testAlreadyHeapifiedArray() {
        Integer[] maxHeap = {9, 6, 8, 3, 5, 7, 4, 1, 2};
        Integer[] expected = Arrays.copyOf(maxHeap, maxHeap.length);
        HeapSort.heapify(maxHeap, Comparator.reverseOrder());
        assertArrayEquals(expected, maxHeap);  // should remain valid max-heap
    }

    @Test
    void testDuplicateElementArray() {
        Integer[] input = {9, 6, 8, 9, 5, 7, 4, 9, 2};
        HeapSort.heapify(input, Comparator.reverseOrder());

        for (int i = 0; i < input.length / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < input.length) {
                assertTrue(input[i] >= input[left]);
            }
            if (right < input.length) {
                assertTrue(input[i] >= input[right]);
            }
        }
    }

    @Test
    void testHeapSort() {
        Integer[] input = {9, 6, 8, 3, 5, 7, 4, 1, 2};
        var sorted_input = Arrays.copyOf(input, input.length);
        Arrays.sort(sorted_input, Comparator.naturalOrder());

        HeapSort.sort(input, Comparator.naturalOrder());
        assertArrayEquals(sorted_input, input);
    }

    @Test
    void testMaxHeapSort() {
        Integer[] input = {9, 6, 8, 3, 5, 7, 4, 1, 2};
        var sorted_input = Arrays.copyOf(input, input.length);
        Arrays.sort(sorted_input, Comparator.reverseOrder());
        HeapSort.sort(input, Comparator.reverseOrder());
        assertArrayEquals(sorted_input, input);
    }

    @Test
    void testCharSort() {
        Character[] input = {'a', 'b', 'c', 'd', 'e', 'f'};
        var sorted_input = Arrays.copyOf(input, input.length);
        Arrays.sort(sorted_input, Comparator.reverseOrder());
        HeapSort.sort(input, Comparator.reverseOrder());
        assertArrayEquals(sorted_input, input);
    }

    @Test
    void testObjectSort() {
        class Person {
            final int age;
            final String name;

            public Person(int age, String name) {
                this.age = age;
                this.name = name;
            }
        }

        var people = new Person[5];
        people[0] = new Person(1, "Alice");
        people[1] = new Person(2, "Bob");
        people[2] = new Person(7, "Charlie");
        people[3] = new Person(4, "Elias");
        people[4] = new Person(5, "Fiona");

        Comparator<Person> ageComparator = Comparator.comparingInt(p1 -> p1.age);

        HeapSort.sort(people, ageComparator.reversed());

        assertEquals(5, people.length);
        assertEquals("Charlie", people[0].name);
        assertEquals("Fiona", people[1].name);
        assertEquals("Elias", people[2].name);
        assertEquals("Bob", people[3].name);
        assertEquals("Alice", people[4].name);

    }

}