package apps.digitakpark.payapp.repositories;

import apps.digitakpark.payapp.model.Debt;

public interface DebtDetailRespository {
    void getDebts(String number, boolean mine);
    void deleteDebt(Debt debt);
//    void deleteDebtHeader(String number, boolean mine);
//    void updateDebtHeader(ContentValues data, String number);
//    void deleteBalance(String number);
//    void updateBalance(ContentValues data, String number);
}
