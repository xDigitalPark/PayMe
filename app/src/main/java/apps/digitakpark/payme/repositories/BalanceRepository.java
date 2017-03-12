package apps.digitakpark.payme.repositories;

import apps.digitakpark.payme.model.Balance;

/**
 * Created by MURRUER on 2/25/2017.
 */
public interface BalanceRepository {
    void getBalances();
    void closeBalance(String number, boolean mine);
    void deleteBalance(Balance balance);
}
