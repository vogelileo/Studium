import java.util.List;

public interface IRingBuffer {
    void append(int item);
    List<Integer> getData();

}
