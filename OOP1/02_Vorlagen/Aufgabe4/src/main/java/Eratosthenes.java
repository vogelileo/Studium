public class Eratosthenes {
    private static final int PRIMES_UP_TO = 100;

    public static void main(String[] args) {

		int i =1;
		long l = 2;
		float f = .1f;
		double d = 0.2;

		float x1 = i/4;
		System.out.println("x1: "+x1);
		double x2 = i/4.0;
		System.out.println("x2: "+x2);
		float x3 = 1.0f/l;
		System.out.println("x3: "+x3);
		double y1 = 123456789 +f;
		System.out.println("y1: "+y1);
		double y2 = 123456789.0 + f;
		System.out.println("y2: "+y2);
		long z1 = Integer.MAX_VALUE +i;
		System.out.println("z1: "+z1);
		long z2 = Integer.MAX_VALUE *2;
		System.out.println("z2: "+z2);
		float z3 = i/0;
		System.out.println("z3: "+z3);


        // The numbers in the sieve start with 2,
        // so we reduce the array length accordingly.
        int[] sieve = generateInitialSieve(PRIMES_UP_TO);

        // Now let's look at each number in the array:
        sieve = processSieveWithEratosthenes(sieve);

        outputSieve(sieve);
    }

    public static int[] generateInitialSieve(int PRIMES_UP_TO) {
        int[] sieve = new int[PRIMES_UP_TO - 1];
        for (int i = 0; i < sieve.length; i++) {
            sieve[i] = i + 2;
        }
        return sieve;
    }

    public static void outputSieve(int[] sieve) {
        System.out.print("Found the following prime numbers: ");
        System.out.print(sieve[0]);
        for (int i = 1; i < sieve.length; i++) {
            if (sieve[i] == -1) {
                continue;
            }
            System.out.print(", " + sieve[i]);
        }
    }

    public static int[] removePrimeMultiplesFromSieve(int primeToRemove, int[] sieve) {
        int numberToCross = primeToRemove * 2;
        while (numberToCross <= PRIMES_UP_TO) {
            // Since ther numbers and indices differ
            // by two, we have to calculate the index:
            int indexToCross = numberToCross - 2;
            // An int[] always needs to contain a valid
            // int, we can't really cross an item, but
            // instead set a special value:
            sieve[indexToCross] = -1;
            // And we continue with the next number:
            numberToCross += primeToRemove;
        }
        return sieve;
    }

    public static int[] processSieveWithEratosthenes(int[] sieve) {
        for (int primeNumber : sieve) {
            // Has the number already been crossed out?
            if (primeNumber == -1) {
                continue;
            }

            sieve = removePrimeMultiplesFromSieve(primeNumber, sieve);
        }
        return sieve;
    }
}
