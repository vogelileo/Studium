import java.util.ArrayList;
import java.util.List;

class RingBuffer implements IRingBuffer {
    private int[] buffer;
    private int size;
    private int start;
    private int end;
    private boolean isFull;

    public RingBuffer(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.size = size;
        this.buffer = new int[size];
        this.start = 0;
        this.end = 0;
        this.isFull = false;
    }

    public void append(int item) {
        buffer[end] = item;
        end = (end + 1) % size;
        if (isFull) {
            start = (start + 1) % size;
        }
        if (end == start) {
            isFull = true;
        }
    }

    public List<Integer> getData() {
        List<Integer> items = new ArrayList<>();
        if (!isFull) {
            for (int i = 0; i < end; i++) {
                items.add(buffer[i]);
            }
        } else {
            for (int i = start; i < size; i++) {
                items.add(buffer[i]);
            }
            for (int i = 0; i < end; i++) {
                items.add(buffer[i]);
            }
        }
        return items;
    }
}
