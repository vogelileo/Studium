import java.util.Arrays;

public class ArrayFunctions {
	public static void main(String[] args) {

	}

	public static void print(int[] values) {
		System.out.println(Arrays.toString(values));
	}

	public static int[] revert(int[] values) {
		if(values == null) return null;
		int[] newValues = new int[values.length];
		for (int i = values.length -1; i >= 0 ; i--){
			newValues[values.length -i -1] = values[i];
		}
		return newValues;
	}

	public static boolean descendinglySorted(int[] values){
		if(values == null) return false;

		boolean stillTrue = true;
		for(int i = 1; i < values.length;i ++){
			if(values[i-1] < values[i]){
				stillTrue = false;
			}
		}
		return stillTrue;

	}

	public static boolean ascendinglySorted(int[] values){
		if(values == null) return false;

		boolean stillTrue = true;
		for(int i = 1; i < values.length;i ++){
			if(values[i-1] > values[i]){
				stillTrue = false;
			}
		}
		return stillTrue;

	}
}
