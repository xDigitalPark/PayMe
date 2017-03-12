package apps.digitakpark.payme.detail;

import apps.digitakpark.payme.model.DebtHeader;
import apps.digitakpark.payme.repositories.DebtDetailRespository;
import apps.digitakpark.payme.repositories.DebtRepository;
import apps.digitakpark.payme.model.Debt;
import apps.digitakpark.payme.repositories.DebtDetailRepositoryImpl;
import apps.digitakpark.payme.repositories.DebtRepositoryImpl;

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
