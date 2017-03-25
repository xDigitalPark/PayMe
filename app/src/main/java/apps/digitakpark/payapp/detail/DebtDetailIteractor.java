package apps.digitakpark.payapp.detail;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;

public interface DebtDetailIteractor {
    void doRetrieveDebtList(String number, boolean mine);
    void doChangeContactLink(String currentNumber, boolean mine, String number, String name);
    void doCloseDebt(Debt debt);
    void doDeleteDebt(Debt debt);
    void doDeleteDebtHeader(DebtHeader debtHeader);
}
