package payme.apps.seven.org.payme.repositories;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface CreateDebtRepository {
    void createDebt(DebtHeader debtHeader, Debt debt);
    boolean createDebtHeader(DebtHeader debtHeader);
    boolean createDebt(Debt debt);
    boolean createBalance(DebtHeader debtHeader, Debt debt);
}
