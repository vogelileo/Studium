public class PairTest {
    public static void main(String[] args) {
        testOrderedPairGetters();
        testUnorderedPairGetters();
        testOrderedPairEquals();
        testUnorderedPairEquals();
        testInterPairEquals();
    }

    private static void testOrderedPairGetters() {
        Pair<Integer, String> a = new OrderedPair<>(1, "A");
        check("Ordered first getter", a.getFirst().equals(1));
        check("Ordered second getter", a.getSecond().equals("A"));
    }

    private static void testUnorderedPairGetters() {
        Pair<Integer, Integer> a = new UnorderedPair<>(1, 2);
        check("Unordered first getter", a.getFirst().equals(1));
        check("Unordered second getter", a.getSecond().equals(2));
    }

    private static void testOrderedPairEquals() {
        var a = new OrderedPair<>(1, "A");
        check("Ordered reflexive equality", a.equals(a));
        check("Ordered null inequality", !a.equals(null));
        var b = new OrderedPair<>(1, "A");
        check("Order regular equality case 1", a.equals(b));
        check("Order regular equality case 2", b.equals(a));
        check("Order regular equality case 3", a.hashCode() == b.hashCode());
        var c = new OrderedPair<>(1, 2);
        var d = new OrderedPair<>(2, 1);
        check("Differently ordered pairs", !c.equals(d));
    }

    private static void testUnorderedPairEquals() {
        var a = new UnorderedPair<>(1, 2);
        check("Unordered reflexive equality", a.equals(a));
        check("Unordered null inequality", !a.equals(null));
        var b = new UnorderedPair<>(1, 2);
        check("Unordered regular equality case 1", a.equals(b));
        check("Unordered regular equality case 2", b.equals(a));
        check("Unordered regular equality case 3", a.hashCode() == b.hashCode());
        var c = new UnorderedPair<>(2, 1);
        check("Unordered regular equality case 4", a.equals(c));
        check("Unordered regular equality case 5", c.equals(a));
        check("Unordered regular equality case 6", a.hashCode() == c.hashCode());
    }

    @SuppressWarnings("unlikely-arg-type")
    private static void testInterPairEquals() {
        var a = new OrderedPair<>(1, 2);
        var b = new UnorderedPair<>(1, 2);
        check("Inter pair inequality case 1", !a.equals(b));
        check("Inter pair inequality case 2", !b.equals(a));
    }

    private static void check(String message, boolean condition) {
        System.out.println(message + (condition ? " PASSED" : " FAILED"));
    }
}
