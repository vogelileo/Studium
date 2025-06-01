import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class SortedSnapshotArrayListIterator<T > implements Iterator<T> {
    private final ArrayList<T> arrayList;
    private int currentIndex;

    public SortedSnapshotArrayListIterator(T[] elements, int size, Comparator<T> comparator){
        this.arrayList = new ArrayList<>(comparator);
        T[] sortedArray = Arrays.copyOf(elements, size);
        Arrays.sort(sortedArray, comparator);
        this.arrayList.addAll(sortedArray);
        System.out.println(Arrays.toString(sortedArray));
        this.currentIndex = 0;
    }



    @Override
    public boolean hasNext() {
        return currentIndex < arrayList.size();
    }

    @Override
    public T next() {
        if(this.hasNext()){

            T e = arrayList.get(currentIndex);
            currentIndex++;
            return e;
        }
        return null;

    }
}