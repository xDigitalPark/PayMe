package apps.digitakpark.payapp.detail.ui;

import android.net.Uri;
import android.os.Bundle;

import java.util.List;
import java.util.Map;

import apps.digitakpark.payapp.model.Debt;

public interface DebtDetailView {

    int CLOSE_DEBT = 0;
    int DELETE_DEBT = 1;
    int EDIT_DEBT = 2;

    void prepopActivity(Bundle extras);
    void onLoadDebtList(List<Debt> debtList);
    void onLoadTotal(Double total);
    void navigateToCreateDebtActivity(boolean mine);
    void navigateToPaymentsActivity(Debt debt);
    void onDebtSelected(Debt debt, int actionId);
    void onCloseDebtOptionSelected(Debt debt);
    void onDeleteDebtOptionSelected(Debt debt);
    void onEditDebtOptionSelected(Debt debt);
    void onDebtDeleted(Long id);
    void onDebtHeaderDeleted();
    void callToNumber(String number);
    void onPickedContact(Uri contactData);
    void showPickContactActivity();
    void updateContactInfo(Map<String, String> data);
    void showMessage(String message);

}
