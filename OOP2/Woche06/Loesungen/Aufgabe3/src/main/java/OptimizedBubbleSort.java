import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class OptimizedBubbleSort {

    public static void bubbleSort(int[] array) {
        if(array==null || array.length==0) {
            return;
        }

        int size = array.length;

        for (int i = 0; i < size - 1; i++)
            for (int j = 0; j < size - i - 1; j++)
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
    }

    public static void optimizedBubbleSort(int[] array){
        if(array==null || array.length==0) {
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
        int[] a = generateRandomSortedIntArray(500000);
        int[] b = Arrays.copyOf(a, a.length);

        long start1 = System.currentTimeMillis();
        optimizedBubbleSort(b);
        System.out.println((System.currentTimeMillis() - start1));

        long start = System.currentTimeMillis();
        bubbleSort(a);
        System.out.println((System.currentTimeMillis() - start));


    }

    private static int[] generateRandomSortedIntArray(int numberOfElements) {
        return IntStream.generate(() -> new Random().nextInt(numberOfElements)).limit(numberOfElements).sorted().toArray();
    }
}
