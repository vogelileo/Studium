import java.util.Random;
import java.util.stream.IntStream;

public class Maximum {

    //O(n^2)
  /*  public int findMaximum(int[] nums) {
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
    }*/

    //O(n)
    public int findMaximum(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    //O(n^2)
    public static void main(String[] args) {
        int[]  randomIntArray = IntStream.generate(() -> new Random().nextInt(100000)).limit(10000000).toArray();
        Maximum maximum = new Maximum();

        var startMs = System.currentTimeMillis();
        System.out.println(maximum.findMaximum(randomIntArray));
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));
    }
}
