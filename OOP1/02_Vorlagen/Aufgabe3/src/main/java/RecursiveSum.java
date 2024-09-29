import java.util.Arrays;
import java.util.Random;

public class RecursiveSum {
	public static void main(String[] args) {
		int[] series = randomSeries(1000);
		// Compute the sum of the series recursively
		System.out.println(recursiveSum( series));
	}

	public static int recursiveSum( int[]series){
		if(series.length == 1){
			return series[0];
		}else{

			return series[0] + recursiveSum( Arrays.copyOfRange(series, 1, series.length ));

		}

	}
	
	static int[] randomSeries(int amount) {
		Random random = new Random(4711);
		int[] series = new int[amount];
		for (int index = 0; index < amount; index++) {
			series[index] = random.nextInt(10);
		}
		return series;
	}
}
