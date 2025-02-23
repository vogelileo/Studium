import java.util.Objects;

public class UnorderedPair<T,U> implements Pair<T,U> {
    Object first;
    Object second;

    @Override
    public Object getFirst() {
        return first;
    }

    @Override
    public Object getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o){
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        OrderedPair obj = (OrderedPair<?, ?>) o;
        return Objects.equals(first, obj.first) && Objects.equals(second, obj.second) ||
                Objects.equals(first, obj.second) && Objects.equals(second, obj.first);
    }

    public UnorderedPair(Object first, Object second){
        if(first == null || second == null){
            throw new IllegalArgumentException();
        }
        this.first = first;
        this.second = second;
    }
}
