package task4.serialization;
import com.fasterxml.jackson.databind.JsonDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import task4.Contact;
import task4.PhoneEntry;

import java.io.IOException;

public class ContactJsonDeserializer extends JsonDeserializer<Contact> {
    @Override
    public Contact deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        var tree = jsonParser.readValueAs(JsonNode.class);
        
        var name = tree.get("name").asText();
        var address = tree.get("address").asText();
        var contact = new Contact(name, address);
        var phoneEntries = tree.get("phoneEntries");
        for (JsonNode phoneEntryNode : phoneEntries) {
            var phoneEntry = context.readTreeAsValue(phoneEntryNode, PhoneEntry.class);
            contact.addPhoneEntry(phoneEntry);
        }

        return contact;
    }
}
