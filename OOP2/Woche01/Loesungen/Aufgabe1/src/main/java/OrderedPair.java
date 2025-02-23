import java.util.Objects;

public class OrderedPair<T, U> implements Pair<T, U> {
    private final T first;
    private final U second;

    public OrderedPair(T first, U second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("null argument");
        }
        this.first = first;
        this.second = second;
    }

    @Override
    public T getFirst() {
        return first;
    }

    @Override
    public U getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first.hashCode(), second.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        var other = (OrderedPair<T, U>)obj;
        return Objects.equals(first, other.first) &&
                Objects.equals(second, other.second);
    }
}
