import java.util.ArrayList;
import java.util.Arrays;

public class Stack {
    private final int capacity;
    private String[] array;

    public void push(String element){
        for(int i = 0; i < this.capacity;i++){
            if(this.array[i] == null){
                this.array[i] = element;
                break;
            }

        }
    }

    public String pop(){
        for(int i = this.capacity -1; i >= 0;i--){
            if(!(this.array[i]== null)){
                String temp = this.array[i];
                this.array[i]= null;
                return temp;
            }
        }
        return null;
    }

    public int size(){
        for(int i = this.array.length -1; i >= 0;i--){
            if(!(this.array[i]== null)){
                return i + 1;
            }
        }
        return 0;
    }

    public boolean isEmpty(){
        for(int i = this.array.length -1; i >= 0;i--){
            if(!(this.array[i]== null)){
                return false;
            }
        }
        return true;
    }

    public boolean isFull(){
        if(this.capacity == 0){
            return true;
        }
        return this.array[this.capacity -1] != null;
    }

    public String[] getArray() {
        return array;
    }

    public Stack(int capacity){
        this.capacity = capacity;
        array = new String[capacity];
    }

    public static void main(String[] args) {
        Stack s = new Stack(5);
        s.push("tets");
        s.push("test");
        System.out.println(Arrays.toString(s.getArray()));
    }
}
