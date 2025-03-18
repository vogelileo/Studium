import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SumCheckerTest {

    @Test
    public void testTwoSumSuccess() {
        SumChecker sm = new SumChecker();
        int[] array = new int[] {1, 2, 3, 5, 6, 8};
        Assertions.assertArrayEquals(new int[]{1, 5}, sm.twoSum(array, 10));
    }

    @Test
    public void testTwoSumEmptyArray() {
        SumChecker sm = new SumChecker();
        int[] array = new int[] {};
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> sm.twoSum(array, 0));
    }

    @Test
    public void testTwoSumNoSumPossible() {
        SumChecker sm = new SumChecker();
        int[] array = new int[] {1, 2, 3};
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> sm.twoSum(array, 10));
    }
}
