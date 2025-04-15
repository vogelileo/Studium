import java.util.Random;
import java.util.stream.IntStream;

public class OptimizedBubbleSort {

    public static void optimizedBubbleSort(int[] array) {
        // TODO Optimize
        if (array == null || array.length == 0) {
            return;
        }

        boolean isSwapped;
        for (int i = 0; i < array.length - 1; i++) {
            isSwapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if(array[j]>array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    isSwapped = true;
                }
            }
            if(!isSwapped)
                break;
        }
    }


    public static void main(String[] args) {
        //50000 -> 444ms not optimized
        int[] a = generateRandomSortedIntArray(50000);

        long start = System.currentTimeMillis();
        optimizedBubbleSort(a);
        System.out.println((System.currentTimeMillis() - start));
    }

    private static int[] generateRandomSortedIntArray(int numberOfElements) {
        return IntStream.generate(() -> new Random().nextInt(numberOfElements)).limit(numberOfElements).sorted().toArray();
    }
}
