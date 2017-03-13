package apps.digitakpark.payapp.list.common;

import apps.digitakpark.payapp.repositories.DebtRepository;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.repositories.DebtRepositoryImpl;
import apps.digitakpark.payapp.repositories.ListDebtRepository;
import apps.digitakpark.payapp.repositories.ListDebtRepositoryImpl;

public class ListDebtIteractorImpl implements ListDebtIteractor {

    private ListDebtRepository repository;
    private DebtRepository debtRepository;

    public ListDebtIteractorImpl() {
        this.repository = new ListDebtRepositoryImpl();
        this.debtRepository = new DebtRepositoryImpl();
    }

    @Override
    public void doRetrieveListHeader(boolean mine) {
        repository.getDebtHeaders(mine);
    }

    @Override
    public void doDeleteDebtHeader(DebtHeader debtHeader) {
        debtRepository.deleteDebtHeader(debtHeader, true, true);
    }
}
