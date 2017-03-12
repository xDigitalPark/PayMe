package apps.digitakpark.payme.create.ui;

import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import apps.digitakpark.payme.events.CreateDebtEvent;
import apps.digitakpark.payme.model.Contact;

public interface CreateDebtView {
    void createNewDebt();
    void hideKeyboard();
    void showProgressBar();
    void hideProgressBar();
    void onDebtCreated(CreateDebtEvent event);
    void showDatePickerDialog();
    void onPickedContact(Uri contactData);
    void showPickContactActivity();
    void updateDateLabel();
    void updateContactInfo(String number, String name);
    void showMessage(String message);
    void prepopActivity(Bundle extras);
    void onContactListLoaded(List<Contact> contactList);
    boolean validateViewForm();

}
