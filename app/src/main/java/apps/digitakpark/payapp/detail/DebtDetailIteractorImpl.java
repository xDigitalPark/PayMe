package apps.digitakpark.payapp.detail;

import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.repositories.DebtDetailRespository;
import apps.digitakpark.payapp.repositories.DebtRepository;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.repositories.DebtDetailRepositoryImpl;
import apps.digitakpark.payapp.repositories.DebtRepositoryImpl;

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
        debtRepository.deleteDebtHeader(debtHeader, true, true, true);
    }

    @Override
    public void doChangeContactLink(String currentNumber, boolean mine, String number, String name) {
        debtRepository.changeDebtContactLink(currentNumber, mine, number, name);
    }
}
