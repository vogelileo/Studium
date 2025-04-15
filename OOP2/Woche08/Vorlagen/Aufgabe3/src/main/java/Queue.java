public class Queue<T> {

    private final ArrayStack<T> mainStack;
    private final ArrayStack<T> supportStack;
    private int size;

    public Queue() {
        mainStack = new ArrayStack<>(8);
        supportStack = new ArrayStack<>(8);
    }

    public void enqueue(T value) {
        //TODO implement
        while(mainStack.size() != 0){
            supportStack.push(mainStack.pop());
        }
        supportStack.push(value);
        while(supportStack.size() != 0){
            mainStack.push(supportStack.pop());
        }
    }

    public T dequeue() throws EmptyQueueException{
        //TODO implement
        if(mainStack.isEmpty()){
            throw new EmptyQueueException("Empty Queue");
        }
        return mainStack.pop();

    }

    public T front() throws EmptyQueueException{
        //TODO implement
        if(mainStack.isEmpty()){
            throw new EmptyQueueException("Empty Queue");
        }
        return mainStack.top();
    }

    public int size() {
        return mainStack.size();
    }
}
