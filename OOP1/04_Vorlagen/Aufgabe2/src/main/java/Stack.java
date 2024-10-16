import java.util.ArrayList;
import java.util.Arrays;

public class Stack {
    private final int capacity;
    private ArrayList<String> array;

    public void push(String element){
       this.array.add(element);
    }

    public String pop(){
        if(this.array.isEmpty()){
            return null;
        }
        String temp = this.array.get(this.array.size()-1);
        this.array.remove((this.array.size()-1));
        return temp;
    }

    public int size(){
       return this.array.size();
    }

    public boolean isEmpty(){
        return this.array.isEmpty();
    }

    public boolean isFull(){
        if(this.capacity == 0){
            return true;
        }
        return this.array.size() == this.capacity-1;
    }

    public ArrayList<String> getArray() {
        return this.array;
    }

    public Stack(int capacity){
        this.capacity = capacity;
        array = new ArrayList<>(capacity);
    }


}
