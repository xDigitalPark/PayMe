package apps.digitakpark.payapp.contacts;

import apps.digitakpark.payapp.events.ContactsEvent;

interface ContactPresenter {
    void onCreate();
    void sendRetrieveContactList();
    void onDestroy();
    void onReceiveContactListEvent(ContactsEvent event);
}
