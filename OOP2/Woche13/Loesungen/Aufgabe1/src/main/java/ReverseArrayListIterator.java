import java.util.Iterator;

public class ReverseArrayListIterator<T> implements Iterator<T> {

    private int currentIndex;
    private final ArrayList<T> arraylist;

    public ReverseArrayListIterator(ArrayList<T> arraylist) {
        this.arraylist = arraylist;
        currentIndex = arraylist.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return currentIndex >= 0;
    }

    @Override
    public T next() {
        return arraylist.get(currentIndex--);
    }
}
