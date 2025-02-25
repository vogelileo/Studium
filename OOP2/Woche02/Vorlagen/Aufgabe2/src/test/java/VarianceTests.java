import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class VarianceTests {

    @Test
    public void testSumDouble() {
        List<Double> numbers = List.of(1.0, 2.0, 3.0, 4.0);
        Assertions.assertEquals(10.0, VarianceExamples.sum(numbers), 0.001);
    }

    @Test
    public void testSumInteger() {
        List<Integer> numbers = List.of(1, 2, 3, 4);
        Assertions.assertEquals(10.0, VarianceExamples.sum(numbers), 0.001);
    }

    @Test
    public void testSumNumber() {
        List<Number> numbers = List.of(1, 2, 3.0, 4.0);
        Assertions.assertEquals(10.0, VarianceExamples.sum(numbers), 0.001);
    }

    @Test
    public void testAddNumbersInteger() {
        List<Integer> numbers = new ArrayList<>();
        List<Integer> source = List.of(1, 2, 3, 4);
        VarianceExamples.addNumbers(numbers, source);
        Assertions.assertIterableEquals(numbers, source);
    }

    @Test
    public void testAddNumbersNumber() {
        List<Number> numbers = new ArrayList<>();
        List<Integer> source = List.of(1, 2, 3, 4);
        VarianceExamples.addNumbers(numbers, source);
        Assertions.assertIterableEquals(numbers, source);
    }

    @Test
    public void testFindMaxInteger() {
        List<Integer> source = List.of(1, 2, 3, 4);
        Assertions.assertEquals(4, VarianceExamples.findMax(source));
    }

    @Test
    public void testFindMaxString() {
        List<String> source = List.of("a", "b", "c", "d");
        Assertions.assertEquals("d", VarianceExamples.findMax(source));
    }

    @Test
    public void testFilterByTypeInteger() {
        List<Object> mixedList = List.of(1, "hello", 2.5, 3);
        Assertions.assertIterableEquals(List.of(1, 3), VarianceExamples.filterByType(mixedList, Integer.class));
    }

    @Test
    public void testFilterByTypeNumber() {
        List<Object> mixedList = List.of(1, "hello", 2.5, 3);
        Assertions.assertIterableEquals(List.of(1, 2.5, 3), VarianceExamples.filterByType(mixedList, Number.class));
    }

    @Test
    public void testFilterByTypeString() {
        List<Object> mixedList = List.of(1, "hello", 2.5, 3);
        Assertions.assertIterableEquals(List.of("hello"), VarianceExamples.filterByType(mixedList, String.class));
    }
}
