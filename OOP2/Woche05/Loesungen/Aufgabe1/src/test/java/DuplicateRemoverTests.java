import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DuplicateRemoverTests {

    @Test
    public void testDuplicateRemoverSuccess() {
        DuplicateRemover dp = new DuplicateRemover();
        int[] array = new int[] {10, 10, 1, 3, 4};
        Assertions.assertArrayEquals(new int[]{10, 1, 3, 4}, dp.removeDuplicates(array));
    }

    @Test
    public void testDuplicateRemoverEmptyArray() {
        DuplicateRemover dp = new DuplicateRemover();
        int[] array = new int[] {};
        Assertions.assertArrayEquals(new int[]{}, dp.removeDuplicates(array));
    }

    @Test
    public void testDuplicateRemoverNoDuplicates() {
        DuplicateRemover dp = new DuplicateRemover();
        int[] array = new int[] {10, 1, 3, 4};
        Assertions.assertArrayEquals(new int[]{10, 1, 3, 4}, dp.removeDuplicates(array));
    }
}
