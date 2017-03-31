package apps.digitakpark.payapp.contacts;

import apps.digitakpark.payapp.events.ContactsEvent;

public interface ContactPresenter {
    void onCreate();
    void sendRetrieveContactList();
    void onDestroy();
    void onReceiveContactListEvent(ContactsEvent event);
    void doSendAddContact(String number, String name);
}
