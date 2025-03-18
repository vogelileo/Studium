import java.util.Random;
import java.util.stream.IntStream;

public class SumChecker {

    //O(n^2)
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    public static void main(String[] args) {
        int[]  randomIntArray = IntStream.generate(() -> new Random().nextInt(1000000)).limit(10000000).toArray();
        randomIntArray[randomIntArray.length - 1] = 150;
        randomIntArray[randomIntArray.length - 2] = 350;
        SumChecker sm = new SumChecker();

        var startMs = System.currentTimeMillis();
        sm.twoSum(randomIntArray, 500);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));
    }
}
