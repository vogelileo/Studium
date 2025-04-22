import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;


public class TestPriorityQueue {

    AdaptablePriorityQueue<Integer, String> priorityQueue;

    @BeforeEach
    public void setUp() {
        priorityQueue = new AdaptablePriorityQueue<>(Comparator.comparingInt(o -> o));
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

    @Test
    public void test04_Remove() {
        priorityQueue.insert(2, "Two");
        priorityQueue.insert(3, "Three");
        priorityQueue.insert(1, "One");
        Entry<Integer, String> toRemove = new Entry<>(3, "Three");
        assertEquals(toRemove, priorityQueue.remove(toRemove));
        toRemove = new Entry<>(1, "One");
        assertEquals(toRemove, priorityQueue.remove(toRemove));
        toRemove = new Entry<>(2, "Two");
        assertEquals(toRemove, priorityQueue.remove(toRemove));
        assertNull(priorityQueue.remove(new Entry<>(1, "One")));
    }

    @Test
    public void test05_ReplaceValue() {
        priorityQueue.insert(2, "Two");
        priorityQueue.insert(3, "Three");
        priorityQueue.insert(1, "One");
        assertTrue(priorityQueue.replaceValue(new Entry<>(1, "One"), "Test1"));
        assertTrue(priorityQueue.replaceValue(new Entry<>(3, "Three"), "Test3"));
        assertTrue(priorityQueue.replaceValue(new Entry<>(2, "Two"), "Test2"));
        assertEquals("Test1", priorityQueue.removeMin().getValue());
        assertEquals("Test2", priorityQueue.removeMin().getValue());
        assertEquals("Test3", priorityQueue.removeMin().getValue());
        assertFalse(priorityQueue.replaceValue(new Entry<>(9, "Nine"), ":("));
    }

    @Test
    public void test06_ReplaceKey() {
        priorityQueue.insert(2, "Two");
        priorityQueue.insert(3, "Three");
        priorityQueue.insert(9, "Nine");
        assertTrue(priorityQueue.replaceKey(new Entry<>(9, "Nine"), 1));
        assertEquals(new Entry<>(1, "Nine"), priorityQueue.removeMin());
        assertEquals(new Entry<>(2, "Two"), priorityQueue.min());
        assertTrue(priorityQueue.replaceKey(new Entry<>(2, "Two"), 1));
        assertEquals(new Entry<>(1, "Two"), priorityQueue.min());
        assertFalse(priorityQueue.replaceKey(new Entry<>(2, "Two"), 10));
        assertTrue(priorityQueue.replaceKey(new Entry<>(1, "Two"), 10));
    }
}
