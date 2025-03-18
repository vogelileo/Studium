import java.util.*;
import java.util.stream.IntStream;

public class DuplicateRemover {

    // The Code runs in O(n³)
    public int[] removeDuplicates_slow(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] == nums[j]) {
                    for (int k = j; k < n - 1; k++) {
                        nums[k] = nums[k + 1];
                    }
                    n--;
                    j--;
                }
            }
        }
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = nums[i];
        }
        return result;
    }

    // The Code runs in O(n)
    // This is because it iterates over the input array once, performing constant-time operations for each element.
    // Additionally, it utilizes a HashSet to efficiently check for duplicates,
    // which has an average constant-time complexity for insertion and lookup.
    // Thus, the overall time complexity is linear with respect to the size of the input array.
    public int[] removeDuplicates(int[] nums) {
        Set<Integer> set = new HashSet<>();
        List<Integer> resultList = new ArrayList<>();
        for (int num : nums) {
            if (set.add(num)) {
                resultList.add(num);
            }
        }
        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        int[]  randomIntArray = IntStream.generate(() -> new Random().nextInt(100000)).limit(100000).toArray();
        DuplicateRemover dp = new DuplicateRemover();
        System.out.println("Time Slow version: ");
        var startMs = System.currentTimeMillis();
        dp.removeDuplicates_slow(randomIntArray);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));

        System.out.println("Time Fast version: ");
        startMs = System.currentTimeMillis();
        dp.removeDuplicates(randomIntArray);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));
    }
}
