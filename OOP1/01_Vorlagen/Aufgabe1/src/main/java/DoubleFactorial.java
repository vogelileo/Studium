public class DoubleFactorial {
	public static void main(String[] args) {
	System.out.println(doubleFactorial(10));
	}
	private static int doubleFactorial(int number){
		int product = 1;
		for(int i =  number; i >0;i-=2){
			product *= i;
			System.out.println(i);
		}

		return product;
	}
}
