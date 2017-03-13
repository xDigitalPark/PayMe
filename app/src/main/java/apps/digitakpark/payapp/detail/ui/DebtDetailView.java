package apps.digitakpark.payapp.detail.ui;

import android.os.Bundle;

import java.util.List;

import apps.digitakpark.payapp.model.Debt;

public interface DebtDetailView {

    int CLOSE_DEBT = 0;
    int DELETE_DEBT = 1;

    void prepopActivity(Bundle extras);
    void onLoadDebtList(List<Debt> debtList);
    void onLoadTotal(Double total);
    void navigateToCreateDebtActivity(boolean mine);
    void onDebtSelected(Debt debt, int actionId);
    void onCloseDebtOptionSelected(Debt debt);
    void onDeleteDebtOptionSelected(Debt debt);
    void onDebtDeleted(Long id);
    void onDebtHeaderDeleted();
    void callToNumber(String number);
}