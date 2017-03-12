package apps.digitakpark.payme.repositories;

import apps.digitakpark.payme.model.Debt;
import apps.digitakpark.payme.model.DebtHeader;

public interface CreateDebtRepository {
    void createDebt(DebtHeader debtHeader, Debt debt);
    boolean createDebtHeader(DebtHeader debtHeader);
    boolean createDebt(Debt debt);
    boolean createBalance(DebtHeader debtHeader, Debt debt);
}
