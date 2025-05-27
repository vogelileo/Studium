public class Pair<T1, T2> {
    private final T1 list1Element;
    private final T2 list2Element;

    public Pair(T1 list1Element, T2 list2Element) {
        this.list1Element = list1Element;
        this.list2Element = list2Element;
    }

    public T1 getList1Element() {
        return list1Element;
    }

    public T2 getList2Element() {
        return list2Element;
    }
}