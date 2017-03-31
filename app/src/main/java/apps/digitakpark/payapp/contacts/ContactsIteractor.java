package apps.digitakpark.payapp.contacts;

public interface ContactsIteractor {
    void doRetrieveContactsList();

    void doAddContact(String number, String name);
}
