import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;


public class TestPriorityQueue {

    PriorityQueue<Integer, String> priorityQueue;

    @BeforeEach
    public void setUp() {
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
    }

    @Test
    public void test01_Init() {
        assertEquals(0, priorityQueue.size());
        assertTrue(priorityQueue.isEmpty());
        assertThrows(EmptyPriorityQueueException.class, () -> priorityQueue.removeMin());
    }

    @Test
    public void test02_Insert() {
        priorityQueue.insert(2, "Two");
        assertEquals(1, priorityQueue.size());
        assertEquals(2, priorityQueue.min().getKey());
        assertEquals("Two", priorityQueue.min().getValue());
        priorityQueue.insert(3, "Three");
        assertEquals(2, priorityQueue.size());
        assertEquals(2, priorityQueue.min().getKey());
        assertEquals("Two", priorityQueue.min().getValue());
        priorityQueue.insert(1, "One");
        assertEquals(3, priorityQueue.size());
        assertEquals(1, priorityQueue.min().getKey());
        assertEquals("One", priorityQueue.min().getValue());
    }

    @Test
    public void test03_RemoveMin() {
        priorityQueue.insert(2, "Two");
        priorityQueue.insert(3, "Three");
        priorityQueue.insert(1, "One");
        Entry<Integer, String> entry = priorityQueue.removeMin();
        assertEquals(1, entry.getKey());
        assertEquals("One", entry.getValue());
        entry = priorityQueue.removeMin();
        assertEquals(2, entry.getKey());
        assertEquals("Two", entry.getValue());
        entry = priorityQueue.removeMin();
        assertEquals(3, entry.getKey());
        assertEquals("Three", entry.getValue());
    }
}
