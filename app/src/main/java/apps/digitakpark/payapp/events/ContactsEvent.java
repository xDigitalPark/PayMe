package apps.digitakpark.payapp.events;

import java.util.List;

import apps.digitakpark.payapp.model.Contact;

/**
 * Created by MURRUER on 3/30/2017.
 */

public class ContactsEvent {
    public static final int CONTACT_LIST_OK = 0x01;
    public static final int CONTACT_ADDED_OK = 0x02;
    public static final int CONTACT_REMOVE_OK = 0x03;
    private int status;
    private String message;
    private List<Contact> contactList;


    public static int getContactListOk() {
        return CONTACT_LIST_OK;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

}
