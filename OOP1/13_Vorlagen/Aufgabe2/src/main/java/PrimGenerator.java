import java.util.stream.IntStream;

public class PrimGenerator {
    public static void main(String[] args) {
        int LIMIT = 1000000;
        long startTime1 = System.nanoTime();
        IntStream.iterate(0, i->i+1)
                .parallel()
                .filter(PrimGenerator::isPrime)
                .limit(LIMIT)
                .forEachOrdered(System.out::println);
        long endTime1 = System.nanoTime();
        long startTime2 = System.nanoTime();
        IntStream.iterate(0, i->i+1)
                .filter(PrimGenerator::isPrime)
                .limit(LIMIT)
                .forEach(System.out::println);
        long endTime2 = System.nanoTime();

        long count = IntStream.iterate(0, i->i+1)
                .limit(LIMIT)
                .filter(PrimGenerator::isPrime)
                .count();

        double  elapsed1 = (endTime1-startTime1) / 1_000_000.0;
        double  elapsed2 =( endTime2-startTime2) / 1_000_000.0;

        System.out.println("Execution time in milliseconds for EFFICIENT: " + elapsed1 );
        System.out.println("Execution time in milliseconds for INEFFICIENT: " + elapsed2 );
        System.out.println("So much better: " +String.format("%.02f", ((elapsed2 / elapsed1 )*100)) + "%");
        System.out.println("Wow that many prime Numbers between 0 and " + LIMIT +": "+ count);
    }

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false; // 0, 1, and negative numbers are not prime
        }
        if (n <= 3) {
            return true; // 2 and 3 are prime
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false; // Eliminate multiples of 2 and 3
        }
        // Check divisors from 5 to √n, skipping even numbers
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean primeTester(int number){
        for(int i = 2; i< number; i++){
            if(number % i == 0)
                return false;
        }
        return true;
    }
}
