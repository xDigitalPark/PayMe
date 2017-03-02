package payme.apps.seven.org.payme.create;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;
import payme.apps.seven.org.payme.repositories.CreateDebtRepository;
import payme.apps.seven.org.payme.repositories.CreateDebtRepositoryImpl;

public class CreateDebtIteractorImpl implements CreateDebtIteractor {

    private CreateDebtRepository repository;

    public CreateDebtIteractorImpl() {
        this.repository = new CreateDebtRepositoryImpl();
    }

    @Override
    public void doCreateDebt(DebtHeader debtHeader, Debt debt) {
        repository.createDebt(debtHeader, debt);
    }

}
