package payme.apps.seven.org.payme.balance;

import payme.apps.seven.org.payme.model.Balance;
import payme.apps.seven.org.payme.repositories.BalanceRepository;
import payme.apps.seven.org.payme.repositories.BalanceRepositoryImpl;

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
