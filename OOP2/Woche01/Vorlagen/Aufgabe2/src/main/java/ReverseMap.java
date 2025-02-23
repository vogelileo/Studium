import java.util.Collection;
import java.util.HashMap;

public class ReverseMap<L, R> {
    HashMap<R, L> leftHash = new HashMap<>();
    HashMap<L, R> rightHash = new HashMap<>();

    public void put(L left, R right){
        if(rightHash.containsKey(left) || leftHash.containsKey(right)){
            throw new IllegalArgumentException();
        }
        rightHash.put(left, right);
        leftHash.put(right, left);
    }

    public R getRight(Object s){
        return rightHash.get(s);
    }

    public L  getLeft(Object s){
        return leftHash.get(s);
    }

    public Collection<L>  leftValues(){
        return rightHash.keySet();
    }

    public Collection<R> rightValues(){
        return leftHash.keySet();
    }

    public int size(){
        return leftHash.size();
    }

    public void clear(){
        leftHash.clear();
        rightHash.clear();
    }

}
