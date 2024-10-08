import java.util.ArrayList;

public class Eratosthenes {
    public static void main(String[] args) {
        //Array Generator
        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 1; i < 100; i++) {
            input.add(i);
        }
        //Function call
        System.out.println(eratorsthenes(input));
    }

    private static ArrayList<Integer> eratorsthenes(ArrayList<Integer> inputArray) {
        int originalSize = inputArray.size();
        for (int i = 2; i <= originalSize; i++) {
            int multiplier = 2;
            while (i * multiplier <= originalSize) {
                inputArray.remove(Integer.valueOf(multiplier * i));
                multiplier++;
            }
        }

        return inputArray;
    }
}
