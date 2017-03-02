package payme.apps.seven.org.payme.create.ui;

import android.net.Uri;
import android.os.Bundle;

import payme.apps.seven.org.payme.events.DebtEvent;

public interface CreateDebtView {
    void createNewDebt();
    void hideKeyboard();
    void showProgressBar();
    void hideProgressBar();
    void onDebtCreated(DebtEvent event);
    void showDatePickerDialog();
    void onPickedContact(Uri contactData);
    void showPickContactActivity();
    void updateDateLabel();
    void updateContactInfo(String number, String name);
    void showMessage(String message);
    void prepopActivity(Bundle extras);
    boolean validateViewForm();
}
