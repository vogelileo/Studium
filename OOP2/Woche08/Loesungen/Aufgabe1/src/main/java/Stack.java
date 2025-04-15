public interface Stack<T> {
    int size();
    boolean isEmpty();

    T top() throws EmptyStackException;
    void push(T element) throws StackOverflowException;

    T pop() throws EmptyStackException;
}
