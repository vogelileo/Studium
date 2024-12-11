package task4.serialization;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import task4.Contact;
import task4.ContactBook;

public class ContactBookJsonSerializer extends JsonSerializer<ContactBook> {
    @Override
    public void serialize(ContactBook contactBook, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        // TODO serialize the contacts array
        jsonGenerator.writeEndObject();
    }

}
