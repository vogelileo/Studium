import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueueTests {

    @Test
    public void testEnqueue() {
        Queue<Integer> queue = new ArrayQueue<>();
        queue.enqueue(2);
        Assertions.assertEquals(1, queue.size());
        Assertions.assertEquals(2, queue.getArraySize());

        queue.enqueue(5);
        Assertions.assertEquals(2, queue.size());
        Assertions.assertEquals(2, queue.getArraySize());

        queue.enqueue(6);
        Assertions.assertEquals(3, queue.size());
        Assertions.assertEquals(4, queue.getArraySize());
    }

    @Test
    public void testDequeue() {
        Queue<Integer> queue = new ArrayQueue<>();
        queue.enqueue(2);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        Assertions.assertEquals(2, queue.front());
        queue.dequeue();
        Assertions.assertEquals(3,queue.size());
        Assertions.assertEquals(4, queue.getArraySize());
        Assertions.assertEquals(5, queue.front());

        queue.dequeue();
        Assertions.assertEquals(2,queue.size());
        Assertions.assertEquals(2, queue.getArraySize());
        Assertions.assertEquals(6, queue.front());
    }
}
