public class QueueImpl<T> implements Queue<T> {

    private T[] array;
    private int size;
    private int front;

    public QueueImpl() {
        array = (T[]) new Object[2];
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
    public T front() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("The Queue is empty");
        }
        return array[front];
    }

    @Override
    public void enqueue(T element) {
        if (size == array.length) {
            enlargeArray();
        }
        int rear = (front + size) % array.length;
        array[rear] = element;
        size++;
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw (new EmptyQueueException(
                    "The Queue is empty."));
        }
        T obj = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        if ((size >= 2) && (size == array.length / 2)) {
            reduceArray();
        }
        return obj;
    }

    private void enlargeArray() {
        T[] prev = array;
        array = (T[]) new Object[array.length * 2];
        copyToArray(prev);
        front = 0;
    }

    private void reduceArray() {
        T[] prev = array;
        if (prev.length % 2 == 0) {
            array = (T[]) new Object[prev.length / 2];
        } else {
            array = (T[]) new Object[prev.length / 2 + 1];
        }
        copyToArray(prev);
        front = 0;
    }

    private void copyToArray(T[] src) {
        int rear = (front + size) % src.length;
        if (front < rear) {
            System.arraycopy(src, front, array, 0, size);
        } else {
            int frontToEndLength = src.length - front;
            System.arraycopy(src, front, array, 0, frontToEndLength);
            System.arraycopy(src, 0, array, frontToEndLength, size - frontToEndLength);
        }
    }

    public static void main(String[] args) {
        QueueImpl<Integer> queue = new QueueImpl<>();
        for (int i = 0; i < 20; i++) {
            System.out.println("enqueue(): " + i);
            queue.enqueue(i);
        }
        System.out.println("front(): " + queue.front());
        while (!queue.isEmpty()) {
            System.out.println("dequeue(): " + queue.dequeue());
        }
    }

    // For Testing only
    @Override
    public int getArraySize() {
        return array.length;
    }
}


