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
    public void testSaveAndLoad() throws ContactBookException {
        ContactBook book = new ContactBook();

        book.addContact("Hans Meier", "Bahnhofstrasse 123, 8001 Zürich");
        book.addNumber("Hans Meier", "043 43 222 11 33", "office");
        book.save();

        ContactBook loadedBook = new ContactBook();
        loadedBook.load();
        Contact contact = loadedBook.findContact("Hans Meier");
        assertNotNull(contact);
        assertEquals("Hans Meier", contact.getName());
        assertEquals("Bahnhofstrasse 123, 8001 Zürich", contact.getAddress());
        assertTrue(hasPhoneNumber(contact, "043 43 222 11 33", "office"));
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
