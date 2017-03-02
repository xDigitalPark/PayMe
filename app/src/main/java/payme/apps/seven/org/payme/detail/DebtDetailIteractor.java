package payme.apps.seven.org.payme.detail;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface DebtDetailIteractor {
    void doRetrieveDebtList(String number, boolean mine);
    void doCloseDebt(Debt debt);
    void doDeleteDebt(Debt debt);
    void doDeleteDebtHeader(DebtHeader debtHeader);
}
