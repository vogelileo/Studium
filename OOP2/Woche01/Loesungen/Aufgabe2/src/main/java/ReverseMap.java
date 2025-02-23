import java.util.Collection;
import java.util.HashMap;

public class ReverseMap<L, R> {
    private final HashMap<R, L> rightToLeft = new HashMap<>();
    private final HashMap<L, R> leftToRight = new HashMap<>();

    public void put(L left, R right) {
        if (leftToRight.containsKey(left) || rightToLeft.containsKey(right)) {
            throw new IllegalArgumentException("left or right value is already associated");
        }
        leftToRight.put(left, right);
        rightToLeft.put(right, left);
    }

    public R getRight(L left) {
        return leftToRight.get(left);
    }

    public L getLeft(R right) {
        return rightToLeft.get(right);
    }

    public Collection<L> leftValues() {
        return rightToLeft.values();
    }

    public Collection<R> rightValues() {
        return leftToRight.values();
    }

    public int size() {
        return rightToLeft.size();
    }

    public void clear() {
        leftToRight.clear();
        rightToLeft.clear();
    }
}
