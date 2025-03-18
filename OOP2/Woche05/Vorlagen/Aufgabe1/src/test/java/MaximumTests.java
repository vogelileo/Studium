import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MaximumTests {

    @Test
    public void testFindMaximumSuccess() {
        Maximum maximum = new Maximum();
        int[] array = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Assertions.assertEquals(10, maximum.findMaximum(array));
    }

    @Test
    public void testFindMaximumEmptyArray() {
        Maximum maximum = new Maximum();
        Assertions.assertEquals(Integer.MIN_VALUE, maximum.findMaximum(new int[]{}));
    }

    @Test
    public void testFindMaximumIntMaxValue() {
        Maximum maximum = new Maximum();
        int[] array = new int[] {1, 2, 3, Integer.MAX_VALUE, 7, 8, 9, 10};
        Assertions.assertEquals(Integer.MAX_VALUE, maximum.findMaximum(array));
    }
}
