public class ListStack<T> implements Stack<T> {

    private Node<T> top;
    private int size;
    private int max;

    public ListStack(int max) {
        this.max = max;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T top() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("The Stack is empty");
        }
        return top.getElement();
    }

    @Override
    public void push(T element) throws StackOverflowException {
        if (size == max) {
            throw new StackOverflowException("Stack already full");
        }
        Node<T> newNode = new Node<>(element);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    @Override
    public T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("The Stack is empty");
        }
        Node<T> currentTop = top;
        top = currentTop.getNext();
        size--;
        return currentTop.getElement();
    }
}
