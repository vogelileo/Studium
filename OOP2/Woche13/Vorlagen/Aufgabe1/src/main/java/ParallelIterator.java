import java.util.Iterator;

public class ParallelIterator<T1, T2> implements Iterator<Pair<T1, T2>> {
    private final Iterator<T1> iter1;
    private final Iterator<T2> iter2;




    public ParallelIterator(Iterator<T1> iter1,Iterator<T2> iter2){
        this.iter1 = iter1;
        this.iter2 = iter2;
    }

    @Override
    public boolean hasNext() {
        return iter1.hasNext() && iter2.hasNext();
    }

    @Override
    public Pair<T1, T2> next() {
        if(this.hasNext()){
            return new Pair<T1, T2>(iter1.next(), iter2.next());
        }
        return null;
    }
}