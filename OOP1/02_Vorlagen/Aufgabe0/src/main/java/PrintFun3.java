public class PrintFun3 {
	public static void main(String[] args) {
		symbolSquare('$', 5);
	}

	static void symbolSquare(char symbol, int amount) {
		int lineAmount = amount;
		for (int i = 1; i <= amount; i++) {
			symbolLine(symbol, lineAmount);
			lineAmount--;
		}
	}

	static void symbolLine(char symbol, int amount) {
		for (int i = 1; i <= amount; i++) {
			System.out.print(symbol);
		}
		System.out.println();
	}
}
