package apps.digitakpark.payapp.repositories;

public interface ContactRepository {

    void getContacts();
    void getContactsList();
    boolean upsertContact(String number, String name);

    void addContact(String number, String name);
}
