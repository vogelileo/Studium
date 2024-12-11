package task4.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import task4.Contact;
import task4.ContactBook;

public class ContactBookJsonDeserializer extends JsonDeserializer<ContactBook> {
    @Override
    public ContactBook deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        var contactBook = new ContactBook();
        var tree = jsonParser.readValueAs(JsonNode.class);
        // TODO deserialize the contacts array

        return contactBook;
    }
}