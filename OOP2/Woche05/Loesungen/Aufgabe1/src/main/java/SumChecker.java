import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class SumChecker {

    // The Code runs in O(n²)
    public int[] twoSum_slow(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    // The Code runs in O(n)
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    public static void main(String[] args) {
        int[]  randomIntArray = IntStream.generate(() -> new Random().nextInt(1000000)).limit(10000000).toArray();
        randomIntArray[randomIntArray.length - 1] = 150;
        randomIntArray[randomIntArray.length - 2] = 350;
        SumChecker sm = new SumChecker();
        System.out.println("Time Slow version: ");
        var startMs = System.currentTimeMillis();
        sm.twoSum_slow(randomIntArray, 500);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));

        System.out.println("Time Fast version: ");
        startMs = System.currentTimeMillis();
        sm.twoSum(randomIntArray, 500);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));
    }
}
