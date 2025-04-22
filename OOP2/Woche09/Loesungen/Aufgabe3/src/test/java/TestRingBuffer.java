import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestRingBuffer {

    @Test
    void testAppendAndGetData() {
        RingBuffer buffer = new RingBuffer(5);
        buffer.append(1);
        buffer.append(2);
        buffer.append(3);
        List<Integer> items = buffer. getData();
        assertEquals(List.of(1, 2, 3), items);
    }

    @Test
    void testOverwrite() {
        RingBuffer buffer = new RingBuffer(3);
        buffer.append(1);
        buffer.append(2);
        buffer.append(3);
        buffer.append(4); // This should overwrite the oldest element (1)
        List<Integer> items = buffer. getData();
        assertEquals(List.of(2, 3, 4), items);
    }

    @Test
    void testFullBuffer() {
        RingBuffer buffer = new RingBuffer(3);
        buffer.append(1);
        buffer.append(2);
        buffer.append(3);
        buffer.append(4);
        buffer.append(5); // This should overwrite the oldest elements (1, 2)
        List<Integer> items = buffer. getData();
        assertEquals(List.of(3, 4, 5), items);
    }

    @Test
    void testEmptyBuffer() {
        RingBuffer buffer = new RingBuffer(3);
        List<Integer> items = buffer. getData();
        assertTrue(items.isEmpty());
    }

    @Test
    void testPartialFill() {
        RingBuffer buffer = new RingBuffer(5);
        buffer.append(1);
        buffer.append(2);
        List<Integer> items = buffer. getData();
        assertEquals(List.of(1, 2), items);
    }

    @Test
    void testOverwriteMultipleTimes() {
        RingBuffer buffer = new RingBuffer(3);
        buffer.append(1);
        buffer.append(2);
        buffer.append(3);
        buffer.append(4);
        buffer.append(5);
        buffer.append(6);
        List<Integer> items = buffer. getData();
        assertEquals(List.of(4, 5, 6), items);
    }

    @Test
    void testSingleElementBuffer() {
        RingBuffer buffer = new RingBuffer(1);
        buffer.append(1);
        assertEquals(List.of(1), buffer. getData());
        buffer.append(2);
        assertEquals(List.of(2), buffer. getData());
    }

    @Test
    void testBufferWithZeroSize() {
        assertThrows(IllegalArgumentException.class, () -> new RingBuffer(0));
    }

    @Test
    void testNegativeBufferSize() {
        assertThrows(IllegalArgumentException.class, () -> new RingBuffer(-1));
    }

    @Test
    void testAppendNull() {
        RingBuffer buffer = new RingBuffer(3);
        buffer.append(1);
        buffer.append(2);
        buffer.append(3);
        buffer.append(0);
        List<Integer> items = buffer. getData();
        assertEquals(List.of(2, 3, 0), items);
    }

}
