package apps.digitakpark.payapp.create.ui;

import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import apps.digitakpark.payapp.events.CreateDebtEvent;
import apps.digitakpark.payapp.model.Contact;

public interface CreateDebtView {
    void createNewDebt();
    void editDebt();
    void hideKeyboard();
    void showProgressBar();
    void hideProgressBar();
    void onDebtCreated(CreateDebtEvent event);
    void showDatePickerDialog();
    void onPickedContact(Uri contactData);
    void showPickContactActivity();
    void updateDateLabel(Long milis);
    void updateContactInfo(String number, String name);
    void showMessage(String message);
    void prepopActivity(Bundle extras);
    void onContactListLoaded(List<Contact> contactList);
    boolean validateViewForm();
    void disableInputsToEdit();
    void onDebtUpdated();

}
