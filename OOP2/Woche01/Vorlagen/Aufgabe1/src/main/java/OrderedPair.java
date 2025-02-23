public class OrderedPair<T,U> implements Pair<T,U> {
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
        return this.first.equals(obj.getFirst())
                && this.second.equals((obj.getSecond()));
    }

    public OrderedPair(Object first, Object second){
        if(first == null || second == null){
            throw new IllegalArgumentException();
        }
        this.first = first;
        this.second = second;
    }
}
