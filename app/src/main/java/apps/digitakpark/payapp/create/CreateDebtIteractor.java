package apps.digitakpark.payapp.create;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;

public interface CreateDebtIteractor {
    void doCreateDebt(DebtHeader debtHeader, Debt debt);
    void doEditDebt(DebtHeader debtHeader, Debt debt, Double editPreTotal);
    void doRetrieveContactList();
}
