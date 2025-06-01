import java.util.Iterator;

public class ArrayListIterator<T> implements Iterator<T> {
    private final ArrayList<T> arrayList;
    private int currentIndex;

    public ArrayListIterator(ArrayList<T> arrayList){
        this.arrayList = arrayList;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex == arrayList.size() - 1;
    }

    @Override
    public T next() {
        if(this.hasNext()){
            currentIndex++;
            return arrayList.get(currentIndex);
        }
        return null;

    }
}