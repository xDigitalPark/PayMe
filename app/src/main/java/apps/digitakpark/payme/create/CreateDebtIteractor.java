package apps.digitakpark.payme.create;

import apps.digitakpark.payme.model.Debt;
import apps.digitakpark.payme.model.DebtHeader;

public interface CreateDebtIteractor {
    void doCreateDebt(DebtHeader debtHeader, Debt debt);
    void doRetrieveContactList();
}
