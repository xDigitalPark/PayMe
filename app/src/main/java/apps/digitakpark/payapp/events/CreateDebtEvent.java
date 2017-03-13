package apps.digitakpark.payapp.events;

import java.util.List;

import apps.digitakpark.payapp.model.Contact;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;

public class CreateDebtEvent {

    public static final short DEBT_CREATED = 0x01;
    public static final short DEBT_CREATION_ERROR = 0x02;
    public static final short CONTACT_LIST_OK = 0x03;

    private short status;
    private Debt debt;
    private DebtHeader debtHeader;
    private String message;
    private List<Contact> contactList;

    public DebtHeader getDebtHeader() {
        return debtHeader;
    }

    public void setDebtHeader(DebtHeader debtHeader) {
        this.debtHeader = debtHeader;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        debt = debt;
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
