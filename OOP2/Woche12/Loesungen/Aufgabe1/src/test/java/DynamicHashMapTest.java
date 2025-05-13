import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DynamicHashMapTest {

    DynamicHashMap<Integer, String> map;

    @BeforeEach
    public void setUp() {
        map = new DynamicHashMap<>();
    }

    @Test
    void putIntegerKeys(){
        var arr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13};

        var original = new ArrayList<String>();
        for (int i : arr){
            var string = "Hallo" + i;
            map.put(i, string);
            original.add(string);
        }

        var result = new ArrayList<String>();
        for (int i : arr){
            result.add(map.get(i));
        }

        assertEquals(original, result);
    }
    @Test
    public void putDuplicateKey() {

        map.put(1, "Hallo1");
        map.put(1, "Hallo2");

        assertEquals("Hallo2", (map.get(1)));
    }

    @Test
    public void putOldValue(){
        map.put(1, "Hallo1");
        var oldValue = map.put(1, "Hallo2");

        assertEquals("Hallo1", oldValue);
    }

    @Test
    public void deleteValue(){
        map.put(1, "Hallo1");
        map.delete(1);
        assertNull(map.get(1));
    }

    @Test
    public void deleteOldValue(){
        map.put(1, "Hallo1");
        var oldValue = map.delete(1);
        assertEquals("Hallo1", oldValue);
    }
}