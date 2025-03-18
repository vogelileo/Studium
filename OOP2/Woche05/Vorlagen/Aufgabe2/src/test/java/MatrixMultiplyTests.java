import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixMultiplyTests {

    @Test
    public void testMatrtixMultiplicationSuccess() throws Exception {
        int[][] A = {
                {1, 1, 1},
                {2, 2, 2},
                {3, 3, 3},
                {4, 4, 4}
        };

        int[][] B = {
                {1, 1, 1, 1},
                {2, 2, 2, 2},
                {3, 3, 3, 3}
        };

        int[][] expectation =
                {
                        {6, 6, 6, 6},
                        {12, 12, 12, 12},
                        {18, 18, 18, 18},
                        {24, 24, 24, 24},
                };

        var result = MatrixMultiply.multiply(A, B);
        Assertions.assertEquals(expectation.length, result.length);
        Assertions.assertEquals(expectation[0].length, result[0].length);

        for (int i = 0; i < expectation.length; i++) {
            for (int i1 = 0; i1 < expectation[i].length; i1++) {
                Assertions.assertEquals(expectation[i][i1], result[i][i1]);
            }
        }
    }

    @Test
    public void testSquareMatrixSuccess() throws Exception {
        int[][] A = {
                {1, 1, 1, 1},
                {2, 2, 2, 2},
                {3, 3, 3, 3},
                {4, 4, 4, 4}
        };

        int[][] B = {
                {1, 1, 1, 1},
                {2, 2, 2, 2},
                {3, 3, 3, 3},
                {4, 4, 4, 4}
        };

        int[][] expectation =
                {
                        {10, 10, 10, 10},
                        {20, 20, 20, 20},
                        {30, 30, 30, 30},
                        {40, 40, 40, 40},
                };

        var result = MatrixMultiply.multiply(A, B);
        Assertions.assertEquals(expectation.length, result.length);
        Assertions.assertEquals(expectation[0].length, result[0].length);

        for (int i = 0; i < expectation.length; i++) {
            for (int i1 = 0; i1 < expectation[i].length; i1++) {
                Assertions.assertEquals(expectation[i][i1], result[i][i1]);
            }
        }
    }

    @Test
    public void testError() {
        int[][] A = {
                {2, 2, 2, 2},
                {3, 3, 3, 3},
                {4, 4, 4, 4}
        };

        int[][] B = {
                {1, 1, 1},
                {2, 2, 2},
                {3, 3, 3}
        };

        Assertions.assertThrows(Exception.class, () -> {
            MatrixMultiply.multiply(A, B);
        });
    }
}
