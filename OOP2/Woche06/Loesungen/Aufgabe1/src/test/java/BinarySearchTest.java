import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BinarySearchTest {

    @Test
    public void TestBinarySearch() {
        String[] array = new String[]{"dirt", "dad", "family", "president", "description", "organization", "dealer", "recognition", "explanation", "safety", "soup", "teacher", "manager", "community", "library", "contract", "lady", "region", "highway", "thought", "assistant", "department", "tradition", "philosophy", "disease", "confusion", "boyfriend", "category", "perception", "people", "excitement", "combination", "strategy", "permission", "fact", "information", "manufacturer", "client", "piano", "goal", "church", "opinion", "elevator", "employment", "celebration", "arrival", "marriage", "artisan", "measurement", "entertainment"};
        Arrays.sort(array);
        int position = BinarySearch.searchBinaryRecursive(Arrays.stream(array).collect(Collectors.toList()), 0, array.length, "dad");
        Assertions.assertEquals(12, position);
    }

    @Test
    public void TestBinarySearchEmptyList() {
        List<String> stringList = new ArrayList<>();
        int position = BinarySearch.searchBinaryRecursive(stringList, 0, stringList.size(), "WhatEver");
        Assertions.assertEquals(-1 , position);
    }

    @Test
    public void TestBinarySearchNoResult() {
        List<String> stringList = new ArrayList<>(Arrays.asList(
                "dirt",
                "family",
                "description",
                "dealer",
                "safety"
        ));

        stringList.sort(Comparator.comparing(String::toString));
        int position = BinarySearch.searchBinaryRecursive(stringList, 0, stringList.size(), "Not Found");
        Assertions.assertEquals(-1, position);
    }
}
