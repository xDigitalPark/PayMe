package payme.apps.seven.org.payme.repositories;

import android.content.ContentValues;

import payme.apps.seven.org.payme.model.Debt;

public interface DebtDetailRespository {
    void getDebts(String number, boolean mine);
    void deleteDebt(Debt debt);
//    void deleteDebtHeader(String number, boolean mine);
//    void updateDebtHeader(ContentValues data, String number);
//    void deleteBalance(String number);
//    void updateBalance(ContentValues data, String number);
}
