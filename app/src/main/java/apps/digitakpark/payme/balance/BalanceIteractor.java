package apps.digitakpark.payme.balance;

import apps.digitakpark.payme.model.Balance;

public interface BalanceIteractor{
    void doRetrieveBalances();
    void doArchiveDebtBalance(Balance balance);
}
