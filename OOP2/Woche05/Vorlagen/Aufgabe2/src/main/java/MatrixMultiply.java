public class MatrixMultiply {
    //O(n^3)
    public static int[][] multiply(int[][] A, int[][] B) throws Exception {
        int aRows = A.length;
        int aCols = A[0].length;

        int bRows = B.length;
        int bCols = B[0].length;

        if (bRows != aCols) {
            throw new Exception("Can't Multiply these matrices");
        }

        int[][] resultMatrix = new int[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < bRows; k++)
                    resultMatrix[i][j] += A[i][k] * B[k][j];
            }
        }

        return resultMatrix;
    }

    public static void printMatrix(int[][] M) {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        for (int[] ints : M) {
            builder.append("\t[ ");
            for (int j = 0; j < M[0].length; j++)
                builder.append(ints[j]).append(" ");

            builder.append("]\n");
        }
        builder.append("\n]");
        System.out.println(builder);
    }
}
