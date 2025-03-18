import java.util.Random;
import java.util.stream.IntStream;

public class Maximum {

    // The Code runs in O(n²).
    // This is because it uses two nested loops to compare each element with every other element in the array.
    public int findMaximum_slow(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            boolean isMax = true;
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[j] > nums[i]) {
                    isMax = false;
                    break;
                }
            }
            if (isMax) {
                max = Math.max(max, nums[i]);
            }
        }
        return max;
    }

    // The Code runs in O(n).
    // The overall time complexity is linear with respect to the size of the input array.
    public int findMaximum(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        return max;
    }

    public static void main(String[] args) {
        int[]  randomIntArray = IntStream.generate(() -> new Random().nextInt(100000)).limit(10000000).toArray();
        Maximum maximum = new Maximum();
        System.out.println("Time Slow version: ");
        var startMs = System.currentTimeMillis();
        System.out.println(maximum.findMaximum(randomIntArray));
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));

        System.out.println("Time Fast version: ");
        startMs = System.currentTimeMillis();
        System.out.println(maximum.findMaximum_slow(randomIntArray));
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));
    }
}
