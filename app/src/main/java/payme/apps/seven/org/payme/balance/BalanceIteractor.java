package payme.apps.seven.org.payme.balance;

import payme.apps.seven.org.payme.model.Balance;

public interface BalanceIteractor{
    void doRetrieveBalances();
    void doArchiveDebtBalance(Balance balance);
}
