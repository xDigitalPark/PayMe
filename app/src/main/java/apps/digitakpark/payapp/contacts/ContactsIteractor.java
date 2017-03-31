package apps.digitakpark.payapp.contacts;

import apps.digitakpark.payapp.model.Contact;

public interface ContactsIteractor {
    void doRetrieveContactsList();

    void doAddContact(String number, String name);

    void doRemoveContact(Contact contact);
}
