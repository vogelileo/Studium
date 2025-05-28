import java.util.Iterator;

public class ReverseArrayListIterator<T> implements Iterator<T> {
    private final ArrayList<T> arrayList;
    private int currentIndex;

    public ReverseArrayListIterator(ArrayList<T> arrayList){
        this.arrayList = arrayList;
        this.currentIndex = arrayList.size() ;
    }

    @Override
    public boolean hasNext() {
        return currentIndex != 0;
    }

    @Override
    public T next() {
        if(this.hasNext()){
            currentIndex--;
            return arrayList.get(currentIndex);
        }
        return null;

    }
}
