public class ArrayStack<E> {
    private E[] data;
    private int top = -1;

    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public int size() {
        return (top + 1);
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public void push(E element) {
        if(top == data.length - 1) {
            resize(2 * data.length);
        }
        data[++top] = element;
    }

    private void resize(int max) {
        E[] temp = (E[]) new Object[max];
        if (top >= 0) System.arraycopy(data, 0, temp, 0, top);
        data = temp;
    }

    public E top() {
        if (isEmpty()) return null;
        return data[top];
    }

    public E pop() {
        if (isEmpty()) return null;
        E element = data[top];
        data[top--] = null;
        return element;
    }
}
