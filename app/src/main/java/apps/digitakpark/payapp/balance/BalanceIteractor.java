package apps.digitakpark.payapp.balance;

import apps.digitakpark.payapp.model.Balance;

public interface BalanceIteractor{
    void doRetrieveBalances();
    void doArchiveDebtBalance(Balance balance);
}
