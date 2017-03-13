package apps.digitakpark.payapp.balance;

import apps.digitakpark.payapp.model.Balance;
import apps.digitakpark.payapp.repositories.BalanceRepository;
import apps.digitakpark.payapp.repositories.BalanceRepositoryImpl;

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
