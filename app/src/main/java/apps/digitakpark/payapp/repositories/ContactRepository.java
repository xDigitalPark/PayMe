package apps.digitakpark.payapp.repositories;

import apps.digitakpark.payapp.model.Contact;

public interface ContactRepository {

    void getContacts();
    void getContactsList();
    boolean upsertContact(String number, String name);

    void addContact(String number, String name);

    void removeContact(Contact contact);
}
