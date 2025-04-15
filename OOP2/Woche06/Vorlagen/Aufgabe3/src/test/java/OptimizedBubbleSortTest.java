import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class OptimizedBubbleSortTest {

    @Test
    public void TestBubbleSort() {
        int[] array = new int[]{312, 2345, 6, 26, 7, 4, 737, 84, 453, 5, 2324, 12, 523};
        int[] resultArray = Arrays.copyOf(array, array.length);
        Arrays.sort(resultArray);

        OptimizedBubbleSort.optimizedBubbleSort(array);
        Assertions.assertArrayEquals(array, resultArray);
    }
}
