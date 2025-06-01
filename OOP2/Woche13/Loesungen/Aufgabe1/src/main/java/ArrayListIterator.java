import java.util.Iterator;

public class ArrayListIterator<T> implements Iterator<T> {

    private int currentIndex;
    private final ArrayList<T> arraylist;

    public ArrayListIterator(ArrayList<T> arraylist) {
        this.arraylist = arraylist;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < arraylist.size();
    }

    @Override
    public T next() {
        return arraylist.get(currentIndex++);
    }
}