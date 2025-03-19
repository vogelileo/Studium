import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import exceptions.JsonSerializationException;

public class ObjectToJsonConverterUnitTest {

    @Test
    public void testSerializeableException() throws JsonSerializationException {
        Object object = new Object();
        ObjectToJsonConverter serializer = new ObjectToJsonConverter();
        assertThrows(JsonSerializationException.class, () -> {
            serializer.convertToJson(object);
        });
    }
    
    @Test
    public void testSerializeable() throws JsonSerializationException {
        User user = new User("frank", "meier", "Franer", "0427177711");
        ObjectToJsonConverter serializer = new ObjectToJsonConverter();
        String jsonString = serializer.convertToJson(user);
        assertEquals("{\"firstName\":\"Frank\",\"lastName\":\"Meier\",\"phoneNumber\":\"0427177711\",\"username\":\"Franer\"}", jsonString);
    }

    @Test
    public void testSerializeable2() throws JsonSerializationException {
        User user = new User("lisa", "kudrow", "Phoebe", "0512903244");
        ObjectToJsonConverter serializer = new ObjectToJsonConverter();
        String jsonString = serializer.convertToJson(user);
        assertEquals("{\"firstName\":\"Lisa\",\"lastName\":\"Kudrow\",\"phoneNumber\":\"0512903244\",\"username\":\"Phoebe\"}", jsonString);
    }
}
