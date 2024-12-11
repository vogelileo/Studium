package task4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactBookTest {

    @Test
    public void testAddContact() {
        ContactBook book = new ContactBook();
        book.addContact("Hans Meier", "Bahnhofstrasse 123, 8001 Zürich");
        Contact contact = book.findContact("Hans Meier");
        assertNotNull(contact);
        assertEquals("Hans Meier", contact.getName());
        assertEquals("Bahnhofstrasse 123, 8001 Zürich", contact.getAddress());
    }

    @Test
    public void testAddNumber() {
        ContactBook book = new ContactBook();

        book.addContact("Hans Meier", "Bahnhofstrasse 123, 8001 Zürich");
        book.addNumber("Hans Meier", "043 43 222 11 33", "office");
        Contact contact = book.findContact("Hans Meier");
        assertTrue(hasPhoneNumber(contact, "043 43 222 11 33", "office"));
    }

    @Test
    public void testJsonSerialization() throws JsonProcessingException {
        ContactBook book = new ContactBook();

        book.addContact("Hans Meier", "Bahnhofstrasse 123, 8001 Zürich");
        book.addNumber("Hans Meier", "043 43 222 11 33", "office");

        book.addContact("Christina Schmidt", "Markplatz 5, 9000 St. Gallen");
        book.addNumber("Christina Schmidt", "043 555 10 01", "office");
        book.addNumber("Christina Schmidt", "079 555 01 10 ", "mobile");

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(book);

        ContactBook readBook = mapper.readValue(jsonString, ContactBook.class);
        Contact contact = readBook.findContact("Hans Meier");

        assertNotNull(contact);
        assertEquals("Hans Meier", contact.getName());
        assertEquals("Bahnhofstrasse 123, 8001 Zürich", contact.getAddress());
        assertTrue(hasPhoneNumber(contact, "043 43 222 11 33", "office"));

        contact = readBook.findContact("Christina Schmidt");
        assertNotNull(contact);
        assertEquals("Christina Schmidt", contact.getName());
        assertEquals("Markplatz 5, 9000 St. Gallen", contact.getAddress());
        assertTrue(hasPhoneNumber(contact, "043 555 10 01", "office"));
        assertTrue(hasPhoneNumber(contact, "079 555 01 10 ", "mobile"));
    }

    private boolean hasPhoneNumber(Contact contact, String number, String description) {
        for (var entry : contact.getPhoneEntries()) {
            if (entry.getNumber().equals(number) && entry.getDescription().equals(description)) {
                return true;
            }
        }
        return false;
    }
}
