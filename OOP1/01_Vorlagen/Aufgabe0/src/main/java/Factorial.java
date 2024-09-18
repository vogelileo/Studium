import java.util.Scanner;

public class Factorial {
	public static void main(String[] arguments) {
		Scanner scanner = new Scanner(System.in);
		int number = scanner.nextInt();
		if (number < 0) {
			System.out.println("Undefined");
		} else if (number > 12) {
			System.out.println("Too large");
		} else {
			System.out.println(factorial(number));
		}
		scanner.close();
	}

	private static int factorial(int number) {
		int product = 1;
		for (int counter = 1; counter <= number; counter++) {
			product *= counter;
		}
		int counter = 1;
		while(counter <= number){
			product *= counter;
			counter++;
		}
		return product;
	}
}
