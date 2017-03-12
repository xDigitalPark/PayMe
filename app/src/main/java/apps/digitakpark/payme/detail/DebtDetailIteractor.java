package apps.digitakpark.payme.detail;

import apps.digitakpark.payme.model.Debt;
import apps.digitakpark.payme.model.DebtHeader;

public interface DebtDetailIteractor {
    void doRetrieveDebtList(String number, boolean mine);
    void doCloseDebt(Debt debt);
    void doDeleteDebt(Debt debt);
    void doDeleteDebtHeader(DebtHeader debtHeader);
}
