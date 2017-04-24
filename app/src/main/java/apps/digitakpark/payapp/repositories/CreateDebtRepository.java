package apps.digitakpark.payapp.repositories;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;

public interface CreateDebtRepository {
    void createDebt(DebtHeader debtHeader, Debt debt);
    boolean createDebtHeader(DebtHeader debtHeader);
    boolean createDebt(Debt debt);
    void editDebt(DebtHeader debtHeader, Debt debt, Double editPreTotal);
    boolean createBalance(DebtHeader debtHeader, Debt debt);
    boolean updateDebtHeader(Debt debt, Double editPreTotal);
}
