import java.util.Iterator;

public class ParallelIterator<T1, T2> implements Iterator<Pair<T1, T2>> {

    private final Iterator<T1> firstIterator;
    private final Iterator<T2> secondIterator;

    public ParallelIterator(Iterator<T1> firstIterator, Iterator<T2> secondIterator) {
        this.firstIterator = firstIterator;
        this.secondIterator = secondIterator;
    }

    public boolean hasNext() {
        return firstIterator.hasNext() && secondIterator.hasNext();
    }

    public Pair<T1, T2> next() {
        return new Pair<>(firstIterator.next(), secondIterator.next());
    }
}