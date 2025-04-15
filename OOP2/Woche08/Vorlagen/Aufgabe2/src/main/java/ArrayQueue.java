import java.util.Arrays;

public class ArrayQueue<T> implements Queue<T> {

    public T[] array;

    public ArrayQueue()
    {
        this.array = (T[]) new Object[2];
    }

    //TODO implement

    private void enLargeArray(){
        T[] newArr = (T[]) new Object[this.size()*2];
        for(int i = 0; i < this.size();i++){
            newArr[i] =this.array[i];
        }
        this.array = newArr;
    }

    private void reduceArray(){
        T[] newArr = (T[]) new Object[(this.array.length + 1) /2];
        for(int i = 0; i < this.size();i++){
            newArr[i] =this.array[i];
        }
        this.array = newArr;
    }

    @Override
    public int size() {
        int counter = 0;
        try{
            while(this.array[counter] != null){
                counter++;
            }
        }catch (ArrayIndexOutOfBoundsException ignored){

        }
        return counter;


    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public T front() throws EmptyQueueException {
        if(this.array[0] == null){
            throw new EmptyQueueException("Empty Queue");
        }
        return this.array[0];
    }

    @Override
    public void enqueue(T element) {
        try{
         T e = this.array[this.size()];
        }catch(IndexOutOfBoundsException e){
            this.enLargeArray();
        }
        array[size()] = element;

    }

    @Override
    public T dequeue() throws EmptyQueueException {
        //TODO check make smaller
        if(this.size() != 0){
            T returnElem = this.array[0];
            for(int i= 0;i < this.size() - 1;i++){
                this.array[i] = this.array[i+1];
            }
            this.array[this.size() -1] = null;
            if(this.size() <= this.array.length /2 ){
                this.reduceArray();
            }
            return returnElem;
        }
        throw new EmptyQueueException("Empty Queue");
    }

    // For Testing only
    @Override
    public int getArraySize() {
        return array.length;
    }
}


