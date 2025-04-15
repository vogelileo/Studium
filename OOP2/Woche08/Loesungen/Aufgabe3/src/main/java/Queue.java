public class Queue<T> {

    private final ArrayStack<T> mainStack;
    private final ArrayStack<T> supportStack;
    private int size;

    public Queue() {
        mainStack = new ArrayStack<>(8);
        supportStack = new ArrayStack<>(8);
    }

    public void enqueue(T value) {
        while (!mainStack.isEmpty()) {
            supportStack.push(mainStack.pop());
        }
        mainStack.push(value);

        while (!supportStack.isEmpty()) {
            mainStack.push(supportStack.pop());
        }
        size++;
    }

    public T dequeue() throws EmptyQueueException{
        if (mainStack.isEmpty()) {
            throw new EmptyQueueException("The Queue is empty");
        }
        size--;
        return mainStack.pop();
    }

    public T front() throws EmptyQueueException{
        if (mainStack.isEmpty()) {
            throw new EmptyQueueException("The Queue is empty");
        }
        return mainStack.top();
    }

    public int size() {
        return size;
    }
}
