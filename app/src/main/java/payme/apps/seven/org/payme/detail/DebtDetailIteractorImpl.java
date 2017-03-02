package payme.apps.seven.org.payme.detail;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;
import payme.apps.seven.org.payme.repositories.DebtDetailRepositoryImpl;
import payme.apps.seven.org.payme.repositories.DebtDetailRespository;
import payme.apps.seven.org.payme.repositories.DebtRepository;
import payme.apps.seven.org.payme.repositories.DebtRepositoryImpl;

public class DebtDetailIteractorImpl implements  DebtDetailIteractor {

    private DebtDetailRespository repository;
    private DebtRepository debtRepository;

    public DebtDetailIteractorImpl() {
        this.repository = new DebtDetailRepositoryImpl();
        this.debtRepository = new DebtRepositoryImpl();
    }

    @Override
    public void doRetrieveDebtList(String number, boolean mine) {
       repository.getDebts(number, mine);
    }

    @Override
    public void doCloseDebt(Debt debt) {
        // TODO: Later replace by its own method.
        repository.deleteDebt(debt);
    }

    @Override
    public void doDeleteDebt(Debt debt) {
        repository.deleteDebt(debt);
    }

    @Override
    public void doDeleteDebtHeader(DebtHeader debtHeader) {
        debtRepository.deleteDebtHeader(debtHeader, false, true);
    }
}
