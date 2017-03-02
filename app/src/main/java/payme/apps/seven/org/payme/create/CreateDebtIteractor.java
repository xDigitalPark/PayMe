package payme.apps.seven.org.payme.create;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface CreateDebtIteractor {
    void doCreateDebt(DebtHeader debtHeader, Debt debt);
}
