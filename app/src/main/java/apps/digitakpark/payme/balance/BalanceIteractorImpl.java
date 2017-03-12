package apps.digitakpark.payme.balance;

import apps.digitakpark.payme.model.Balance;
import apps.digitakpark.payme.repositories.BalanceRepository;
import apps.digitakpark.payme.repositories.BalanceRepositoryImpl;

public class BalanceIteractorImpl implements  BalanceIteractor{

    private BalanceRepository repository;

    public BalanceIteractorImpl() {
        this.repository = new BalanceRepositoryImpl();
    }

    @Override
    public void doRetrieveBalances() {
        repository.getBalances();
    }

    @Override
    public void doArchiveDebtBalance(Balance balance) {
        repository.deleteBalance(balance);
    }
}
