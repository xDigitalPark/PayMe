package apps.digitakpark.payapp.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.events.ContactsEvent;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.events.CreateDebtEvent;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Contact;

public class ContactRepositoryImpl implements ContactRepository {
    private EventBus eventBus;
    private DatabaseAdapter database;
    private DebtLookupRepository debtLookupRepository;

    public ContactRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.database = PaymeApplication.getDatabaseInstance();
        this.debtLookupRepository = new DebtLookupRepositoryImpl();
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

    @Override
    public void getContactsList() {
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
            ContactsEvent event = new ContactsEvent();
            event.setStatus(ContactsEvent.CONTACT_LIST_OK);
            event.setMessage("OK");
            event.setContactList(contactList);
            eventBus.post(event);
        }
    }

    @Override
    public boolean upsertContact(String number, String name) {
        Contact foundContact = debtLookupRepository.lookupContact(number);
        if (foundContact == null) {
            ContentValues contactData = new ContentValues();
            contactData.put(DatabaseAdapter.CONTACT_NUMBER, number);
            contactData.put(DatabaseAdapter.CONTACT_NAME, name);
            return database.insertData(DatabaseAdapter.CONTACT_TABLE, contactData);
        }
        return true;
    }

    @Override
    public void addContact(String number, String name) {
        if (upsertContact(number, name)) {
            ContactsEvent event = new ContactsEvent();
            event.setStatus(ContactsEvent.CONTACT_ADDED_OK);
            event.setMessage("OK");
            eventBus.post(event);
        }
    }

    @Override
    public void removeContact(Contact contact) {
        boolean removed = database.deleteData(DatabaseAdapter.CONTACT_TABLE, "number = ?", contact.getNumber());
        // TODO: Apply validation if has debts.

        if (removed) {
            ContactsEvent event = new ContactsEvent();
            event.setStatus(ContactsEvent.CONTACT_REMOVE_OK);
            event.setMessage("OK");
            eventBus.post(event);
        }
    }
}
