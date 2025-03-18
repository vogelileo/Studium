import java.util.*;
import java.util.stream.IntStream;

public class DuplicateRemover {

    //O(n^3)
   /* public int[] removeDuplicates(int[] nums) {
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
    }*/
    //O(n)
    public int[] removeDuplicates(int[] nums) {
        Set<Integer> intHashSet = new HashSet<>();
        List<Integer> resultList = new ArrayList<>();
        for (int num:nums) {
            if(intHashSet.add(num)){
                resultList.add(num);
            }
        }
        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }



    public static void main(String[] args) {
        int[]  randomIntArray = IntStream.generate(() -> new Random().nextInt(100000)).limit(100000).toArray();
        DuplicateRemover dp = new DuplicateRemover();

        var startMs = System.currentTimeMillis();
        dp.removeDuplicates(randomIntArray);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startMs));
    }
}
