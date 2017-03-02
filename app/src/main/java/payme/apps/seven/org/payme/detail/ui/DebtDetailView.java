package payme.apps.seven.org.payme.detail.ui;

import android.os.Bundle;

import java.util.List;

import payme.apps.seven.org.payme.model.Debt;

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
}
