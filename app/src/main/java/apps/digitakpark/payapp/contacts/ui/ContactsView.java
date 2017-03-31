package apps.digitakpark.payapp.contacts.ui;

import android.net.Uri;

import java.util.List;

import apps.digitakpark.payapp.model.Contact;

public interface ContactsView {

    public final int DELETE_CONTACT = 0x01;

    void onLoadContactList(List<Contact> contactList);
    void showPickContactActivity();
    void onPickedContact(Uri contactData);

    void showContactAdded();

    void onRestartList();
}
