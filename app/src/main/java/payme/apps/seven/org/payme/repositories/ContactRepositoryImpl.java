package payme.apps.seven.org.payme.repositories;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import payme.apps.seven.org.payme.PaymeApplication;
import payme.apps.seven.org.payme.events.CreateDebtEvent;
import payme.apps.seven.org.payme.lib.data.DatabaseAdapter;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;
import payme.apps.seven.org.payme.model.Contact;

public class ContactRepositoryImpl implements ContactRepository {
    private EventBus eventBus;
    private DatabaseAdapter database;

    public ContactRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.database = PaymeApplication.getDatabaseInstance();
    }

    @Override
    public void getContacts() {
        String query = "SELECT id, number, name " +
                "FROM " + DatabaseAdapter.CONTACT_TABLE;
        Cursor cursor = database.retrieveData(query);
        if (cursor != null) {
            List<Contact> contactList = new ArrayList<>();
            Contact contact = null;
            Double total = 0D;
            while (cursor.moveToNext()) {
                contact = new Contact(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getString(cursor.getColumnIndex("name"))
                );
                contactList.add(contact);
            }
            CreateDebtEvent event = new CreateDebtEvent();
            event.setStatus(CreateDebtEvent.CONTACT_LIST_OK);
            event.setMessage("OK");
            event.setContactList(contactList);
            eventBus.post(event);
        }
    }
}
