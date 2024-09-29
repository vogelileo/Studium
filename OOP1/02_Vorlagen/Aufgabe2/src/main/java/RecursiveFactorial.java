public class RecursiveFactorial {
	public static void main(String[] args) {
		System.out.println(recursiveFactorial(4)); // outputs 24
		System.out.println(recursiveFactorial(20)); // outputs -2102132736
		System.out.println(recursiveFactorial(1000)); // outputs 0
		System.out.println(recursiveFactorial(10000)); // outputs 0
		System.out.println(recursiveFactorial(100000)); // outputs StackOverflow

	}

	public static int recursiveFactorial(int amount){
		if(amount == 0) {
			return 1;
		}else{
			return amount * recursiveFactorial(amount - 1);
		}
	}
}
