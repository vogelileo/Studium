import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        testRegularMapping();
        testNullInserts();
        testLeftCollision();
        testRightCollision();
    }

    private static void testRegularMapping() {
        var map = new ReverseMap<String, Integer>();
        map.put("Claudia", 18);
        map.put("Hans", 25);
        map.put("Clara", 30);
        map.put("Paul", 42);
        checkEquals(18, map.getRight("Claudia"));
        checkEquals(25, map.getRight("Hans"));
        checkEquals(30, map.getRight("Clara"));
        checkEquals(42, map.getRight("Paul"));
        checkEquals("Claudia", map.getLeft(18));
        checkEquals("Hans", map.getLeft(25));
        checkEquals("Clara", map.getLeft(30));
        checkEquals("Paul", map.getLeft(42));
        checkEquals(4, map.size());
        checkEquals(new HashSet<>(Arrays.asList("Claudia", "Hans", "Clara", "Paul")), new HashSet<>(map.leftValues()));
        checkEquals(new HashSet<>(Arrays.asList(18, 25, 30, 42)), new HashSet<>(map.rightValues()));
        map.clear();
        checkEquals(0, map.size());
    }

    private static void testNullInserts() {
        var map = new ReverseMap<String, Integer>();
        map.put("Test", null);
        map.put(null, 3);
        checkEquals(null, map.getRight("Test"));
        checkEquals(null, map.getLeft(3));
    }

    private static void testLeftCollision() {
        var map = new ReverseMap<String, Integer>();
        map.put("Test", 4);
        try {
            map.put("Test", 3);
            throw new AssertionError("Undetected collision");
        } catch (IllegalArgumentException e) {
        }
    }

    private static void testRightCollision() {
        ReverseMap<String, Integer> map = new ReverseMap<>();
        map.put("A", 3);
        try {
            map.put("B", 3);
            throw new AssertionError("Undetected collision");
        } catch (IllegalArgumentException e) {
        }
    }

    private static <T> void checkEquals(T expected, T actual) {
        if (Objects.equals(expected, actual)) {
            System.out.println("Passed");
        } else {
            throw new AssertionError("Test failed!");
        }
    }
}
