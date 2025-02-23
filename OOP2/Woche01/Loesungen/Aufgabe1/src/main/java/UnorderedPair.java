import java.util.Objects;

public class UnorderedPair<T> implements Pair<T, T> {
    private final T first;
    private final T second;

    public UnorderedPair(T first, T second) {
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
    public T getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        var other = (UnorderedPair<T>)obj;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second) ||
                Objects.equals(first, other.second) && Objects.equals(second, other.first);
    }
}
