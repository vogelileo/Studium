public class PrimeTester {
	public static void main(String[] args) {
		System.out.println(primeTester(4));
	}

	private static boolean primeTester(int number){
		for(int i = 2; i< number; i++){
			if(number % i == 0)
				return false;
		}
		return true;
	}
}
