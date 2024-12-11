package task4;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import task4.serialization.ContactBookJsonDeserializer;
import task4.serialization.ContactBookJsonSerializer;

import java.io.Serializable;

@JsonDeserialize(using = ContactBookJsonDeserializer.class)
@JsonSerialize(using = ContactBookJsonSerializer.class)
public class ContactBook {
    private static final String FILE_NAME = "contactBook.bin";
    private Map<String, Contact> contactBook = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void load() throws ContactBookException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contactBook = (Map<String, Contact>) stream.readObject();
            System.out.println("Loaded " + contactBook.size() + " contact book entries.");
        } catch (IOException | ClassNotFoundException e) {
            throw new ContactBookException("Error on loading: " + e.getMessage());
        }
    }

    public void save() throws ContactBookException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            stream.writeObject(contactBook);
            System.out.println("Saved " + contactBook.size() + " contact book entries.");
        } catch (IOException e) {
            throw new ContactBookException("Error on saving: " + e.getMessage());
        }
    }

    public void addContact(Contact contact) {
        if (contactBook.containsKey(contact.getName())) {
            throw new IllegalArgumentException("Name already exists");
        }
        contactBook.put(contact.getName(), contact);
    }

    public void addContact(String name, String address) {
        if (contactBook.containsKey(name)) {
            throw new IllegalArgumentException("Name already exists");
        }
        contactBook.put(name, new Contact(name, address));
    }

    public void addNumber(String name, String number, String description) {
        if (!contactBook.containsKey(name)) {
            throw new IllegalArgumentException("Name does not exist");
        }
        Contact contact = contactBook.get(name);
        contact.addPhoneEntry(new PhoneEntry(number, description));
    }

    public Contact findContact(String name) {
        return contactBook.get(name);
    }

    public Collection<Contact> getContacts() {
        return contactBook.values();
    }
}