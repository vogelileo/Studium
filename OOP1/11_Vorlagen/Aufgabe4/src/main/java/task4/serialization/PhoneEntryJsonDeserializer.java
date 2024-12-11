package task4.serialization;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import task4.PhoneEntry;

import java.io.IOException;

public class PhoneEntryJsonDeserializer extends JsonDeserializer<PhoneEntry> {
    @Override
    public PhoneEntry deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String number = node.get("number").asText();
        String description = node.get("description").asText();
        return new PhoneEntry(number, description);
    }
}