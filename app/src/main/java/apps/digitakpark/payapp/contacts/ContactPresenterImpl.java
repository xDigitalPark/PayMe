package apps.digitakpark.payapp.contacts;

import org.greenrobot.eventbus.Subscribe;

import apps.digitakpark.payapp.contacts.ui.ContactsView;
import apps.digitakpark.payapp.events.ContactsEvent;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;

class ContactPresenterImpl implements ContactPresenter {

    private ContactsView view;
    private ContactsIteractor iteractor;
    private EventBus eventBus;

    public ContactPresenterImpl(ContactsView view) {
        this.view = view;
        this.iteractor = new ContactsIteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void sendRetrieveContactList() {
        iteractor.doRetrieveContactsList();
    }

    @Subscribe
    @Override
    public void onReceiveContactListEvent(ContactsEvent event) {
        if(view == null) {
            return;
        }

        switch (event.getStatus()) {
            case ContactsEvent.CONTACT_LIST_OK:
                view.onLoadContactList(event.getContactList());
        }
    }

}
