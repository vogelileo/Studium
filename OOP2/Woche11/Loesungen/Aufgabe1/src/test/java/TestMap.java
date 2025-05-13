import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestMap {

    SeparatChainingMap<Integer, String> map;

    @BeforeEach
    public void setUp() {
        map = new SeparatChainingMap<>();
    }

    @Test
    public void testPut() {
        map.put(6251, "Never");
        map.put(533, "Gonna");
        map.put(421, "Give");
        map.put(756, "You");
        map.put(1923, "Up");
        map.put(53, "Never");
        map.put(339, "Gonna");
        map.put(349, "Let");
        map.put(0, "You");
        assertEquals("Up", map.get(1923));
        assertEquals("Give", map.get(421));

        map.put(31, "Up");
        assertEquals("Up", map.get(31));
        assertEquals("Up", map.put(31, "Down"));
        assertEquals("Down", map.get(31));
    }

    @Test
    public void testRemove() {
        map.put(6251, "A");
        map.put(533, "B");
        map.put(421, "C");
        assertEquals("B", map.remove(533));
        assertEquals("A", map.remove(6251));
        assertEquals("C", map.remove(421));
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @Test
    public void testEntrySet() {
        List<Entry<Integer, String>> list = List.of(
                new Entry<>(421, "C"),
                new Entry<>(6251, "A"),
                new Entry<>(533, "B"),
                new Entry<>(1923, "E"),
                new Entry<>(756, "D")
        );
        map.put(6251, "A");
        map.put(533, "B");
        map.put(421, "C");
        map.put(756, "D");
        map.put(1923, "E");

        assertTrue(list.containsAll(map.entrySet()));
        assertEquals(5, map.size());
    }

    @Test
    public void testValues() {
        List<String> list = List.of(
                "C",
                "E",
                "A",
                "B",
                "D"
        );
        map.put(6251, "A");
        map.put(533, "B");
        map.put(421, "C");
        map.put(756, "D");
        map.put(1923, "E");

        assertTrue(list.containsAll(map.values()));
        assertEquals(5, map.size());
    }

    @Test
    public void testKeySet() {
        Set<Integer> set = Set.of(
                421,
                6251,
                1923,
                533,
                756
        );
        map.put(6251, "A");
        map.put(533, "B");
        map.put(421, "C");
        map.put(756, "D");
        map.put(1923, "E");

        assertTrue(set.containsAll(map.keySet()));
        assertEquals(5, map.size());
    }
}
